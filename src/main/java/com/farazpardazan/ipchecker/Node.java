package com.farazpardazan.ipchecker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class Node {
    public int Value = -1;
    Node[] Children = new Node[256];
}
