package com.Cubix.Jobluu.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "db_sequences")
public class DatabaseSequence {
    @Id
    private String id;
    private Long sequence;  // <— use 'sequence'
}
