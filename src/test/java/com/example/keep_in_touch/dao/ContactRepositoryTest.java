package com.example.keep_in_touch.dao;

import com.example.keep_in_touch.entities.Contact;
import com.example.keep_in_touch.entities.User;
import com.example.keep_in_touch.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ContactRepositoryTest {

    @Mock
    private ContactRepository contactRepository;

    private User testUser;
    private Contact testContact1;
    private Contact testContact2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1);
        testUser.setName("Test User");

        testContact1 = new Contact();
        testContact1.setName("Contact 1");
        testContact1.setUser(testUser);

        testContact2 = new Contact();
        testContact2.setName("Contact 2");
        testContact2.setUser(testUser);
    }

    @Test
    public void testFindContactsByUserIdWithPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Contact> page = new PageImpl<>(Arrays.asList(testContact1, testContact2));

        when(contactRepository.findContactsByUserId(testUser.getId(), pageable)).thenReturn(page);

        Page<Contact> contacts = contactRepository.findContactsByUserId(testUser.getId(), pageable);

        assertThat(contacts).isNotNull();
        assertThat(contacts.getContent().size()).isEqualTo(2);
        assertThat(contacts.getContent().get(0).getName()).isEqualTo("Contact 1");
        assertThat(contacts.getContent().get(1).getName()).isEqualTo("Contact 2");
    }

    @Test
    public void testFindContactsByUserId() {
        when(contactRepository.findContactsByUserId(testUser.getId())).thenReturn(Arrays.asList(testContact1, testContact2));

        List<Contact> contacts = contactRepository.findContactsByUserId(testUser.getId());

        assertThat(contacts).isNotNull();
        assertThat(contacts.size()).isEqualTo(2);
        assertThat(contacts.get(0).getName()).isEqualTo("Contact 1");
        assertThat(contacts.get(1).getName()).isEqualTo("Contact 2");
    }

    @Test
    public void testFindByNameContainingAndUser() {
        when(contactRepository.findByNameContainingAndUser("Contact", testUser)).thenReturn(Arrays.asList(testContact1, testContact2));

        List<Contact> contacts = contactRepository.findByNameContainingAndUser("Contact", testUser);

        assertThat(contacts).isNotNull();
        assertThat(contacts.size()).isEqualTo(2);
        assertThat(contacts.get(0).getName()).isEqualTo("Contact 1");
        assertThat(contacts.get(1).getName()).isEqualTo("Contact 2");
    }

    @Test
    public void testFindByUser() {
        when(contactRepository.findByUser(testUser)).thenReturn(Arrays.asList(testContact1, testContact2));

        List<Contact> contacts = contactRepository.findByUser(testUser);

        assertThat(contacts).isNotNull();
        assertThat(contacts.size()).isEqualTo(2);
        assertThat(contacts.get(0).getName()).isEqualTo("Contact 1");
        assertThat(contacts.get(1).getName()).isEqualTo("Contact 2");
    }
}