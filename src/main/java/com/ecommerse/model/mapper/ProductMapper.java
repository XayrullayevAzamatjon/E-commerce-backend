package com.ecommerse.model.mapper;
import com.ecommerse.entity.CategoryEntity;
import com.ecommerse.entity.ProductEntity;
import com.ecommerse.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "categoryId", source = "category.id")
    public abstract ProductDto toDto(ProductEntity productEntity);

    @Mapping(target = "category.id", source = "category.id")
    @Mapping(target = "id", source = "productDto.id")
    @Mapping(target = "imageUrl", source = "productDto.imageUrl")
    @Mapping(target = "description", source = "productDto.description")
    public abstract ProductEntity toEntity(ProductDto productDto, CategoryEntity category);
}

