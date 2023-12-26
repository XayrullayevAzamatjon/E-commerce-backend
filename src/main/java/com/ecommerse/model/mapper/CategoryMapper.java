package com.ecommerse.model.mapper;


import com.ecommerse.entity.CategoryEntity;
import com.ecommerse.model.CategoryCreateRequest;
import com.ecommerse.model.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CategoryMapper {
    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public  abstract CategoryEntity  toEntity(CategoryCreateRequest request);

    public abstract CategoryResponse toResponse(CategoryEntity category);
    @Mapping(source = "request.categoryName", target = "categoryName")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.imageUrl", target = "imageUrl")
    public abstract CategoryEntity update(CategoryEntity entity,CategoryCreateRequest request);

}
