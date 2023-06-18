package me.plurg.plurg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.plurg.plurg.entity.Trend;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrendResponse {
    private long total;
    private List<Trend> data;
}
