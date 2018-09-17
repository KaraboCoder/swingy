package com.ira.kngwato.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class Enemy {
    private String character;
    private String artifact;
    private int x;
    private int y;
}
