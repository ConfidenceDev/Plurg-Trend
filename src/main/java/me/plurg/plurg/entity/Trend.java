package me.plurg.plurg.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trend {

    @Id
    private String id;
    @Field
    private String tag;
    @Field
    private String msg;
    @Field
    private String url;
    @Field
    private String date;
    @Field
    private Instant utc;
}
