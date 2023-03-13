package sixman.helfit.domain.category.service;

import org.springframework.stereotype.Service;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.category.repository.CategoryRepository;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;

import java.util.Optional;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void verifiedCategoryById(Long categoryId){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        optionalCategory.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUNT));
    }
}
