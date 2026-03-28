package com.pragmafood.talentpool.users.infrastructure.input.rest;

import com.pragmafood.talentpool.users.application.dto.request.CreateClientRequest;
import com.pragmafood.talentpool.users.application.dto.request.CreateEmployeeRequest;
import com.pragmafood.talentpool.users.application.dto.request.CreateOwnerRequest;
import com.pragmafood.talentpool.users.application.dto.response.UserResponse;
import com.pragmafood.talentpool.users.application.handler.user.UserHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for user management")
public class UserRestController {

    private final UserHandler userHandler;

    @Operation(summary = "Create owner user", description = "Creates a new owner user in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Owner created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/owner")
    public ResponseEntity<UserResponse> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.createOwner(request));
    }

    @Operation(summary = "Create employee user", description = "Creates a new employee user in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Employee created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/employee")
    public ResponseEntity<UserResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.createEmployee(request));
    }

    @Operation(summary = "Create client user", description = "Creates a new client user in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Client created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/client")
    public ResponseEntity<UserResponse> createClient(@Valid @RequestBody CreateClientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.createClient(request));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userHandler.getUserById(id));
    }
}
