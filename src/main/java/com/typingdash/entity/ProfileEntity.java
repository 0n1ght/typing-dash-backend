package com.typingdash.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

@Embeddable
@Data
@NoArgsConstructor
public class ProfileEntity {
    private String nickname;
    private int lvl = 1;
    private short currentSpeed = 0;
    private short highestSpeed = 0;
    private int testsCompleted = 0;
    private Queue<Short> shortTestsHistory = new LinkedList<>();
    private List<Short> longTestsHistory = new LinkedList<>();
    private String iconPath;

    public ProfileEntity(String nickname) {
        this.nickname = nickname;
    }
}
