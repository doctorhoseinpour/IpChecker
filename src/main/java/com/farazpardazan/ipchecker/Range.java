package com.farazpardazan.ipchecker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.management.ConstructorParameters;

@Getter
@Setter
@AllArgsConstructor
public class Range {
    public Long lower;
    public Long upper;
}
