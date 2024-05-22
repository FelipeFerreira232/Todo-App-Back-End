package com.restapi.webservices.security;

public record JwtTokenRequest(String username, String password) {}