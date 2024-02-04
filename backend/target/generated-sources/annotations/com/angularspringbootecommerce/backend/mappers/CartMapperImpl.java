package com.angularspringbootecommerce.backend.mappers;

import com.angularspringbootecommerce.backend.dtos.CartDto;
import com.angularspringbootecommerce.backend.dtos.CartItemDto;
import com.angularspringbootecommerce.backend.models.Cart;
import com.angularspringbootecommerce.backend.models.CartItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-04T19:15:14+0530",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240129-0421, environment: Java 17.0.10 (Eclipse Adoptium)"
)
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDto cartToCartDto(Cart cart, BigDecimal totalPrice, List<CartItemDto> cartItems) {
        if ( cart == null && totalPrice == null && cartItems == null ) {
            return null;
        }

        CartDto cartDto = new CartDto();

        if ( cart != null ) {
            cartDto.setId( cart.getId() );
            cartDto.setTotalPrice( cart.getTotalPrice() );
            cartDto.setUserId( cart.getUserId() );
        }
        List<CartItemDto> list = cartItems;
        if ( list != null ) {
            cartDto.setCartItems( new ArrayList<CartItemDto>( list ) );
        }

        return cartDto;
    }

    @Override
    public CartItemDto cartItemToCartItemDto(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDto cartItemDto = new CartItemDto();

        cartItemDto.setPrice( cartItem.getPrice() );
        cartItemDto.setProductId( cartItem.getProductId() );
        cartItemDto.setProductName( cartItem.getProductName() );
        cartItemDto.setQuantity( cartItem.getQuantity() );

        cartItemDto.setSubTotal( cartItem.getSubTotal() );

        return cartItemDto;
    }
}
