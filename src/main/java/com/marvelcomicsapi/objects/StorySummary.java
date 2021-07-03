package com.marvelcomicsapi.objects;

import com.marvelcomicsapi.objects.ref.Summary;

public class StorySummary extends Summary {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
