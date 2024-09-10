// Функция для переключения видимости сайдбара  ЭТО ТОЖЕ МОЖНО УДАЛИТЬ хотя хз
const toggleSideBar = () => {
    if ($(".sidebar").is(":visible")) {
        //close sidebar
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
        $(".content").css("width", "100%");
    } else {
        //open sidebar
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "23%");
    }
}


// Функция для поиска контактов
const search = () => {
    let query = $("#search-input").val(); // Получаем значение из поля ввода
    if (query === '') {
        $(".search-result").hide();  // Если запрос пустой, скрываем результаты поиска
    } else {
        let url = `http://localhost:8080/search/${query}`;  // Формируем URL для запроса
        fetch(url).then((response) => response.json())  // Обрабатываем ответ как JSON
            .then((data) => {
                let text = `<div class='list-group'>`; // Создаем контейнер для списка результатов
                data.forEach(contact => {
                    text += `<a href='/user/${contact.cid}/contact' class='list-group-item list-group-action'>${contact.name}</a>`;
                });
                text += `</div>`;
                $(".search-result").html(text); // Обновляем HTML с результатами
                $(".search-result").show(); // Показываем результаты
            });
    }
}



// Функция для поиска пользователей  ЭТО ТОЖЕ МОЖНО УДАЛИТЬ
const searchForUser = () => {
    let query = $("#search-input").val();   // Получаем значение из поля ввода
    if (query === '') {
        $(".search-result").hide();   // Если запрос пустой, скрываем результаты поиска
    } else {
        let url = `http://localhost:8080/search-user/${query}`;  // Формируем URL для запроса
        fetch(url).then((response) => response.json())    // Обрабатываем ответ как JSON
            .then((data) => {
                let text = `<div class='list-group'>`;  // Создаем контейнер для списка результатов
                data.forEach(user => {
                    text += `<a href='/admin/user-profile/${user.id}' class='list-group-item list-group-action'>${user.name}</a>`;
                });
                text += `</div>`;
                $(".search-result").html(text); // Обновляем HTML с результатами
                $(".search-result").show(); // Показываем результаты
            });
    }
}

<!-- Функция для удаления пользователя с подтверждением  -->
function deleteUser(id) {
    swal({
        title: "Are you sure admin?",
        text: "Once deleted, you will not be able to recover this contact !NEVER!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                // Если пользователь подтвердил удаление,
                window.location = '/admin/user-delete/' + id;
            } else {
                // Иначе
                swal("Huh!Your user is safe!");
            }
        });
}

<!-- 08,09 start  -->
// Function to sort table
function sortTable() {
    const table = document.getElementById("userTable").getElementsByTagName('tbody')[0];
    const rows = Array.from(table.getElementsByTagName('tr'));

    // Находим строку с администратором
    const adminRow = rows.find(row => row.querySelector('td:nth-child(2)').textContent === 'Administrator');
    const otherRows = rows.filter(row => row.querySelector('td:nth-child(2)').textContent !== 'Administrator');


    // Сортируем остальные строки по имени
    otherRows.sort((a, b) => {
        const nameA = a.querySelector('td:nth-child(2)').textContent.toUpperCase();
        const nameB = b.querySelector('td:nth-child(2)').textContent.toUpperCase();
        return nameA.localeCompare(nameB);
    });

    // Clear the table
    table.innerHTML = "";

    // Add the sorted rows back to the table
    if (adminRow) {
        table.appendChild(adminRow);
    }
    otherRows.forEach(row => table.appendChild(row));
}


// Выполняем сортировку таблицы после загрузки документа

document.addEventListener('DOMContentLoaded', function () {
    sortTable();
});
<!-- 08,09 end-->

<!-- Скрипт для управления активными элементами меню  setting -->

$(document).ready(() => {
    $(".item").removeClass("active");
    $("#settings-link").addClass("active");
    $(".nav-item").removeClass("active");
    $("#settings-route").addClass("active");
});


<!-- Функция для удаления контакта с подтверждением через SweetAlert   это normal-base   это удаляем в профайле . после поиска  вью контакт -->
function deleteContact(cId) {
    swal({
        title: "HEY!Are you sure?",
        text: "If you do it now, you can not recover this contact! NEVER!!!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                window.location = '/user/delete/' + cId;
            } else {
                swal("Huh!Your contact is safe !!!");
            }
        });
}





<!--  END   это prof-base -->



