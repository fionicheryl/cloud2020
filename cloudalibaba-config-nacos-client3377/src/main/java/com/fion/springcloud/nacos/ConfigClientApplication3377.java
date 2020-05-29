package com.fion.springcloud.nacos;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
public class ConfigClientApplication3377 {

    private ViewLoginInfoService viewLoginInfoService;

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication3377.class, args);
    }

    /*public static void main(String[] args) {
        ConfigClientApplication3377 configClientApplication3377 = new ConfigClientApplication3377();
        FirstPageChartVo vo = configClientApplication3377.getOnlineDate();
        System.out.println(vo);
    }*/

    public List<String> generateTimeDistances() {
        List<String> timeDistances = new ArrayList<>();
        for (int i = 0; i < 48; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            if (i % 2 == 0) {
                String hour = String.format("%02d", i / 2);
                stringBuilder.append(hour).append(":00-").append(hour).append(":30");
            } else {
                String preHour = String.format("%02d", (i - 1) / 2);
                String postHour = String.format("%02d", (i + 1) / 2);
                stringBuilder.append(preHour).append(":30-").append(postHour).append(":00");
            }
            timeDistances.add(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
        }
        return timeDistances;
    }

    public FirstPageChartVo getOnlineDate() {
        // List<FirstPageVo> firstPageVos = viewLoginInfoService.getTest();
        List<FirstPageVo> firstPageVos = buildData();
        if (CollectionUtils.isEmpty(firstPageVos)) {
            // 没查出任何数据，直接返回空就行
            return new FirstPageChartVo();
        }
        // 构造 time->pick 的映射
        Map<String, Integer> time2PickMap = firstPageVos.stream().collect(Collectors.toMap(FirstPageVo::getTimeDitance, v -> Integer.parseInt(v.getPickUpNumber())));
        // 构造 time->give 的映射
        Map<String, Integer> time2GiveMap = firstPageVos.stream().collect(Collectors.toMap(FirstPageVo::getTimeDitance, v -> Integer.parseInt(v.getGiveUpNumber())));

        // 定义返回数据图表
        FirstPageChartVo chartVo = new FirstPageChartVo();
        // 获取所有时间段，枚举中已经定义好时间段
        List<String> timeDistances = generateTimeDistances();
        List<Integer> pickUpNumbers = new ArrayList<>();
        List<Integer> giveUpNumbers = new ArrayList<>();
        List<Double> rates = new ArrayList<>();
        // 遍历时间段
        timeDistances.stream().forEach(v -> {
            if (time2PickMap.keySet().contains(v)) {
                // 结果包含该时间段，用结果返回的自身的值
                int pick = time2PickMap.get(v);
                int give = time2GiveMap.get(v);
                pickUpNumbers.add(pick);
                giveUpNumbers.add(give);
                double rate = pick / (pick + give);
                rates.add(Double.parseDouble(String.format("%.4f", rate)));
            } else {
                // 结果不包含，使用默认值0
                pickUpNumbers.add(0);
                giveUpNumbers.add(0);
                rates.add(0D);
            }
        });

        // 设置图表对象，即包括四个数组
        chartVo.setTimeDistances(timeDistances);
        chartVo.setPickUpNumbers(pickUpNumbers);
        chartVo.setGiveUpNumbers(giveUpNumbers);
        chartVo.setRates(rates);
        return chartVo;
    }

    public List<FirstPageVo> buildData() {
        List<FirstPageVo> firstPageVos = new ArrayList<>();
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("10:00-10:30").pickUpNumber("4").giveUpNumber("3").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("13:00-13:30").pickUpNumber("6").giveUpNumber("0").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("13:30-14:00").pickUpNumber("2").giveUpNumber("0").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("14:00-14:30").pickUpNumber("3").giveUpNumber("0").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("14:30-15:00").pickUpNumber("6").giveUpNumber("0").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("15:00-15:30").pickUpNumber("4").giveUpNumber("0").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("15:30-16:00").pickUpNumber("4").giveUpNumber("0").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("17:00-17:30").pickUpNumber("4").giveUpNumber("0").build());
        return firstPageVos;
    }
}

@Data
@Builder
class FirstPageVo {
    private String searchDate;
    private String timeDitance;
    private String pickUpNumber;
    private String giveUpNumber;
}

@Data
class FirstPageChartVo {
    List<String> timeDistances;
    List<Integer> pickUpNumbers;
    List<Integer> giveUpNumbers;
    List<Double> rates;
}




