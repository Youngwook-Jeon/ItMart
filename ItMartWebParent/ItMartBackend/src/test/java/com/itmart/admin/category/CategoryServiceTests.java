package com.itmart.admin.category;

import com.itmart.itmartcommon.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @Spy
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @Test
    public void testCheck_Unique_InNewMode_Return_DuplicateName() {
        Long id = null;
        String name = "Electronics";
        String alias = "spring";
        Category category = new Category(id, name, alias);
        Mockito.when(repository.findByName(name))
                .thenReturn(category);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheck_Unique_InNewMode_Return_DuplicateAlias() {
        Long id = null;
        String name = "spring";
        String alias = "Electronics";
        Category category = new Category(id, name, alias);
        Mockito.when(repository.findByName(name))
                .thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void testCheck_Unique_InNewMode_Return_OK() {
        Long id = null;
        String name = "spring";
        String alias = "Electronics";
        Category category = new Category(id, name, alias);
        Mockito.when(repository.findByName(name))
                .thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void testCheck_Unique_InEditMode_Return_DuplicateName() {
        Long id = 1L;
        String name = "Electronics";
        String alias = "spring";
        Category category = new Category(2L, name, alias);
        Mockito.when(repository.findByName(name))
                .thenReturn(category);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheck_Unique_InEditMode_Return_DuplicateAlias() {
        Long id = 1L;
        String name = "spring";
        String alias = "Electronics";
        Category category = new Category(2L, name, alias);
        Mockito.when(repository.findByName(name))
                .thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void testCheck_Unique_InEditMode_Return_OK() {
        Long id = 1L;
        String name = "spring";
        String alias = "Electronics";
        Category category = new Category(id, name, alias);
        Mockito.when(repository.findByName(name))
                .thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("OK");
    }
}
