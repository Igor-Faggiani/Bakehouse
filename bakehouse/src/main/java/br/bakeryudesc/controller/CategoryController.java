package br.bakeryudesc.controller;

import br.bakeryudesc.dao.CategoryDAO;
import br.bakeryudesc.model.Category;
import br.bakeryudesc.utils.PersistenceInstance;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryController {

    private final EntityManager entityManager;
    private final CategoryDAO categoryDAO;

    public CategoryController() {
        this.entityManager = PersistenceInstance.getEntityManager();
        this.categoryDAO = new CategoryDAO(entityManager);
    }

    public void createCategory(String name) {
        if (categoryDAO.findByName(name) != null) {
            throw new RuntimeException("Error: Already exist a category with this '" + name + "' name.");
        }

        Category newCategory = new Category();
        newCategory.setName(name);

        categoryDAO.save(newCategory);
    }

    public void updateCategory(Category category) {
        if (category != null) {
            Category existing = categoryDAO.findByName(category.getName());
            if (existing != null && !existing.getId().equals(category.getId())) {
                throw new RuntimeException("Error: The name '" + category.getName() + "' is already in use.");
            }
            categoryDAO.update(category);
        }
    }

    public void deleteCategory(Category category) {
        categoryDAO.softDelete(category);
    }

    public Category findCategoryById(Long categoryId) {
        return categoryDAO.findById(categoryId);
    }

    public List<Category> findAllCategories() {
        List<Category> categories = categoryDAO.findAll();
        return categories.stream().filter(category -> category.getDeletedAt() == null).toList();
    }

    public void close() {
        if (this.entityManager != null && this.entityManager.isOpen()) {
            this.entityManager.close();
        }
    }
}
