package ru.lenivtsev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lenivtsev.model.dto.ProductDto;
import ru.lenivtsev.model.mapper.ProductDtoMapper;
import ru.lenivtsev.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Pattern PARAM_PATTERN_BETWEEN = Pattern.compile("(\\d+)?\\-(\\d+)?");
    private final ProductRepository productRepository;
    private final ProductDtoMapper mapper;

    public Page<ProductDto> findAllByFilter(String productTitleFilter, String costFilter, int page, int size, String sortField){
        productTitleFilter = productTitleFilter == null || productTitleFilter.isBlank() ? null : "%" + productTitleFilter.trim() + "%";
        costFilter = costFilter == null || costFilter.isBlank() ? null : costFilter.trim();
        BigDecimal costFilterMin = null;
        BigDecimal costFilterMax = null;
        if (costFilter!=null){
            Matcher matcher = PARAM_PATTERN_BETWEEN.matcher(costFilter);

            if (matcher.matches()) {
                costFilterMin = matcher.group(1)==null ? null : new BigDecimal(matcher.group(1));
                costFilterMax = matcher.group(2)==null ? null : new BigDecimal(matcher.group(2));
            }
        }
        return productRepository.productByFilter(productTitleFilter, costFilterMin, costFilterMax, PageRequest.of(page, size, Sort.by(sortField)))
                .map(mapper::map);
    }

    public Optional<ProductDto> findByProductId(Long id) {
        return productRepository.findById(id).map(mapper::map);
    }

    public void save(ProductDto productDto) {
        productRepository.save(mapper.map(productDto));
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
}
