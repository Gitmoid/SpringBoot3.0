package com.springbootlearning.learningspringboot3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoreDomainTest {

    @Test
    void testNoArgConstructorInitializesFieldsToNull() {
        VideoEntity entity = new VideoEntity();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getUsername()).isNull();
        assertThat(entity.getName()).isNull();
        assertThat(entity.getDescription()).isNull();
    }

    @Test
    void newVideoEntityHasNullId() {
        VideoEntity entity = new VideoEntity("alice", "title", "description");
        assertThat(entity.getId()).isNull();
        assertThat(entity.getUsername()).isEqualTo("alice");
        assertThat(entity.getName()).isEqualTo("title");
        assertThat(entity.getDescription()).isEqualTo("description");
    }

    @Test
    void toStringReturnsCorrectStringRepresentation() {
        VideoEntity entity = new VideoEntity("alice", "title", "description");
        assertThat(entity.toString())
                .isEqualTo("VideoEntity{id=null, username='alice', name='title', description='description'}");
    }

    @Test
    void settersMutateState() {
        VideoEntity entity = new VideoEntity("alice", "title", "description");
        entity.setId(99L);
        entity.setUsername("bob");
        entity.setName("new name");
        entity.setDescription("new description");
        assertThat(entity.getId()).isEqualTo(99L);
        assertThat(entity.getUsername()).isEqualTo("bob");
        assertThat(entity.getName()).isEqualTo("new name");
        assertThat(entity.getDescription()).isEqualTo("new description");
    }
}
