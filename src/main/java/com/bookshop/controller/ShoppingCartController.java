package com.bookshop.controller;

import com.bookshop.dto.shoppingcart.CreateCartItemRequestDto;
import com.bookshop.dto.shoppingcart.ShoppingCartDto;
import com.bookshop.dto.shoppingcart.UpdateCartItemRequestDto;
import com.bookshop.model.User;
import com.bookshop.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get shopping cart",
            description = "Receive current user's shopping cart")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.findById(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add book to cart",
            description = "Add book to current user's shopping cart")
    public ShoppingCartDto addBookToCart(Authentication authentication,
                                         @RequestBody @Valid CreateCartItemRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToCart(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(("/cart-items/{id}"))
    @Operation(summary = "Update cart item",
            description = "Update cart item by given id")
    public ShoppingCartDto updateCartItem(Authentication authentication,
                                          @PathVariable @Positive
                                          Long id,
                                          @RequestBody @Valid
                                          UpdateCartItemRequestDto quantityDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.update(user.getId(), id, quantityDto);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(("/cart-items/{id}"))
    @Operation(summary = "Delete cart item",
            description = "Delete cart item by given id")
    public ShoppingCartDto deleteCartItem(Authentication authentication,
                                          @PathVariable @Positive Long id) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.removeCartItem(user.getId(), id);
    }
}
