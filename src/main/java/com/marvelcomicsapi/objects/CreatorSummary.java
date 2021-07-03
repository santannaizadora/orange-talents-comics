package com.marvelcomicsapi.objects;

import com.marvelcomicsapi.objects.ref.Summary;

public class CreatorSummary extends Summary {
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
