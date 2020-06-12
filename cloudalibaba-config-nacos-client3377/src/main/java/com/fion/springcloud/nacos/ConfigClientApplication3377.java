package com.fion.springcloud.nacos;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.BetweenFormater.Level;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*@SpringBootApplication
@EnableDiscoveryClient
@Slf4j*/
public class ConfigClientApplication3377 {

    private ViewLoginInfoService viewLoginInfoService;

    private CharNewMapper charNewMapper = new CharNewMapper();

    private ChatNewService chatNewService = new ChatNewService();

    /*public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication3377.class, args);
    }*/

    /*public static void main(String[] args) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        currentDate = "2020-03-20";

        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        *//*ConfigClientApplication3377 configClientApplication3377 = new ConfigClientApplication3377();
        Map<String, FirstPageChartVo> vo = configClientApplication3377.getOnlineDate();
        System.out.println(JSON.toJSONString(vo));*//*
    }*/

    public static void main(String[] args) {
        /*Paging charNewVos = new ConfigClientApplication3377().getWebchatChannelList(null);
        System.out.println(charNewVos);*/
        /*int size = 10;
        int total = 98;
        int current = 9;

        if (total / size + (total % size != 0 ? 1 : 0) == current) {
            System.out.println("last page");
        } else {
            System.out.println("not last page");
        }
        */
        List<LoginMessage> loginMessages = Lists.newArrayList();

        Map<String, String> loginMessageMap = loginMessages.stream()
                .filter(v -> null != v && StrUtil.isNotEmpty(v.getTypename()))
                .collect(Collectors.toMap(LoginMessage::getTypename, v -> formatTimeDiff(Long.parseLong(v.getAccounttime()))));


        System.out.println(formatTimeDiff(3683L ));
    }

    /**
     * 格式化时间戳
     * @param seconds
     * @return
     */
    private static String formatTimeDiff(Long seconds) {
        StringBuilder timeDiff = new StringBuilder();
        // 获得hutool的时间差，精确到秒，格式: a天b小时c分d秒，由于是当天数据，所以最大时间差不会超过天，所以格式为：b小时c分d秒
        String hutoolTimeDiff = DateUtil.formatBetween(seconds * 1000, Level.SECOND);

        if (hutoolTimeDiff.contains(Level.HOUR.getName())) {
            // 截取小时
            String hour = hutoolTimeDiff.substring(0, hutoolTimeDiff.indexOf(Level.HOUR.getName()));
            // 截取分秒
            hutoolTimeDiff = hutoolTimeDiff.substring(hutoolTimeDiff.indexOf(Level.HOUR.getName()) + Level.HOUR.getName().length());
            // 格式化添加小时
            timeDiff.append(String.format("%02d", Long.parseLong(hour)));
        } else {
            timeDiff.append("00");
        }
        timeDiff.append(":");

        if (hutoolTimeDiff.contains(Level.MINUTE.getName())) {
            // 截取分
            String minute = hutoolTimeDiff.substring(0, hutoolTimeDiff.indexOf(Level.MINUTE.getName()));
            // 截取秒
            hutoolTimeDiff = hutoolTimeDiff.substring(hutoolTimeDiff.indexOf(Level.MINUTE.getName()) + Level.MINUTE.getName().length());
            // 格式化添加分
            timeDiff.append(String.format("%02d", Long.parseLong(minute)));
        } else {
            timeDiff.append("00");
        }
        timeDiff.append(":");

        if (hutoolTimeDiff.contains(Level.SECOND.getName())) {
            // 截取秒
            String second = hutoolTimeDiff.substring(0, hutoolTimeDiff.indexOf(Level.SECOND.getName()));
            // 格式化添加秒
            timeDiff.append(String.format("%02d", Long.parseLong(second)));
        } else {
            timeDiff.append("00");
        }

        return timeDiff.toString();
    }

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

    public Map<String, FirstPageChartVo> getOnlineDate() {
        //List<FirstPageVo> firstPageVos = viewLoginInfoService.getTest();
        List<FirstPageVo> firstPageVos = buildData();
        if (CollectionUtils.isEmpty(firstPageVos)) {
            // 没查出任何数据，直接返回空map就行
            return new HashMap<>();
        }
        // 定义返回结果
        Map<String, FirstPageChartVo> result = new HashMap<>();
        // 根据groupId将原始数据分组分组
        Map<String, List<FirstPageVo>> pageGroupMap = firstPageVos.stream().collect(Collectors.groupingBy(FirstPageVo::getGroupId));
        // 根据分组后的信息，去获取图表数据
        pageGroupMap.forEach((key, value) -> result.put(key, getChartVoByGroupData(value)));
        // 返回结果
        return result;
    }

    private FirstPageChartVo getChartVoByGroupData(List<FirstPageVo> firstPageVos) {
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
        List<String> rates = new ArrayList<>();
        // 遍历时间段
        timeDistances.forEach(v -> {
            if (time2PickMap.containsKey(v)) {
                // 结果包含该时间段，用结果返回的自身的值
                int pick = time2PickMap.get(v);
                int give = time2GiveMap.get(v);
                pickUpNumbers.add(pick);
                giveUpNumbers.add(give);
                double rate = (double) pick / (pick + give);
                rates.add(String.format("%.4f", rate));
            } else {
                // 结果不包含，使用默认值0
                pickUpNumbers.add(0);
                giveUpNumbers.add(0);
                rates.add("0");
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
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("10:00-10:30").pickUpNumber("4").giveUpNumber("3").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("13:00-13:30").pickUpNumber("6").giveUpNumber("0").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("13:30-14:00").pickUpNumber("2").giveUpNumber("0").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("14:00-14:30").pickUpNumber("3").giveUpNumber("0").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("14:30-15:00").pickUpNumber("6").giveUpNumber("0").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("15:00-15:30").pickUpNumber("4").giveUpNumber("0").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("15:30-16:00").pickUpNumber("4").giveUpNumber("0").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("17:00-17:30").pickUpNumber("4").giveUpNumber("0").groupId("1").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("10:00-10:30").pickUpNumber("4").giveUpNumber("3").groupId("2").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("13:00-13:30").pickUpNumber("6").giveUpNumber("0").groupId("2").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("13:30-14:00").pickUpNumber("2").giveUpNumber("0").groupId("2").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("14:00-14:30").pickUpNumber("3").giveUpNumber("0").groupId("2").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("14:30-15:00").pickUpNumber("6").giveUpNumber("0").groupId("2").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("15:00-15:30").pickUpNumber("4").giveUpNumber("0").groupId("2").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("15:30-16:00").pickUpNumber("4").giveUpNumber("0").groupId("2").build());
        firstPageVos.add(FirstPageVo.builder().searchDate("2020-05-11").timeDitance("17:00-17:30").pickUpNumber("4").giveUpNumber("0").groupId("2").build());
        return firstPageVos;
    }



    public Paging<ChatNewVo> getWebchatChannelList(CharNewPageParam charNewPageParam) {
        Paging<ChatNew> iPage = charNewMapper.getWebchatChannelList(charNewPageParam);
        if (CollUtil.isEmpty(iPage.getRecords())) {
            // 没有记录，返回空页
            return new Paging<>(iPage.getTotal(), CollUtil.newArrayList(), iPage.getPageIndex(), iPage.getPageSize());
        }

        // 将原记录构造成vo记录
        List<ChatNewVo> chatNewVos = chatNews2ChatNewVos(iPage.getRecords());

        // 最后一页，需要计算总和
        if (iPage.getTotal() / iPage.getPageSize() + (iPage.getTotal() % iPage.getPageSize() != 0 ? 1 : 0) == iPage.getPageIndex()) {
            // 获取总和，并且将记录插入列表
            // chatNewVos.add(getChatNewSum(charNewPageParam));

        }

        // 返回CharNewVo的页面
        return new Paging<>(iPage.getTotal(), chatNewVos, iPage.getPageIndex(), iPage.getPageSize());
    }

    private ChatNew getWebchatChannelTotal(CharNewPageParam charNewPageParam) {
        return new ChatNew();
    }

    private List<ChatNewVo> chatNews2ChatNewVos(List<ChatNew> chatNews) {
        return chatNews.stream().map(v -> {
                            ChatNewVo chatNewVo = new ChatNewVo();
                            // 将对象v中的属性值，copy到charNewVo对象的属性中
                            BeanUtil.copyProperties(v, chatNewVo);
                            return chatNewVo;
                        })
                        .peek(v -> {
                            // 使用已有的值，求和
                            v.setSum(Optional.ofNullable(v.getA()).orElse(BigDecimal.ZERO)
                                    .add(BigDecimal.valueOf(Optional.ofNullable(v.getB()).orElse(0)))
                                    .add(BigDecimal.valueOf(Optional.ofNullable(v.getC()).orElse(0))));
                        })
                        .collect(Collectors.toList());
    }

    private ChatNewVo getChatNewSum(CharNewPageParam charNewPageParam) {
        List<ChatNew> chatNews = chatNewService.getWebchatChannelList(charNewPageParam);
        if (CollUtil.isEmpty(chatNews)) {
            return null;
        }
        // 将原记录构造成vo记录
        List<ChatNewVo> chatNewVos = chatNews2ChatNewVos(chatNews);
        // 定义一个vo记录每个字段求和后的值
        ChatNewVo chatNewVo = new ChatNewVo();
        // 如果数据类型是BigDecimal，就这么求和
        chatNewVo.setA(chatNewVos.stream().map(v -> Optional.ofNullable(v.getA()).orElse(BigDecimal.ZERO)).reduce(BigDecimal.ZERO, BigDecimal::add));
        // 如果是整型，就这么求和
        chatNewVo.setB(chatNewVos.stream().mapToInt(v -> Optional.ofNullable(v.getB()).orElse(0)).sum());
        // 返回总和对象
        return chatNewVo;
    }
}

@Data
@Builder
class FirstPageVo {
    private String searchDate;
    private String timeDitance;
    private String pickUpNumber;
    private String giveUpNumber;
    private String groupId;
}

@Data
class FirstPageChartVo {
    List<String> timeDistances;
    List<Integer> pickUpNumbers;
    List<Integer> giveUpNumbers;
    List<String> rates;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ChatNew {
    BigDecimal a;
    Integer b;
    Integer c;
}

@Data
class ChatNewVo {
    BigDecimal a;
    Integer b;
    Integer c;
    BigDecimal sum;
}

class CharNewPageParam {}

class CharNewMapper {
    Paging<ChatNew> getWebchatChannelList(CharNewPageParam charNewPageParam) {
        List<ChatNew> chatNews = CollUtil.newArrayList();
        for (int i = 0; i < 4; i++) {
            chatNews.add(new ChatNew(BigDecimal.valueOf(i), i + 1, i + 2));
        }
        chatNews.add(new ChatNew(null, null, null));
        Paging<ChatNew> page = new Paging();
        page.setRecords(chatNews);
        return page;
    }
}

class ChatNewService {
    List<ChatNew> getWebchatChannelList(CharNewPageParam charNewPageParam) {
        List<ChatNew> chatNews = CollUtil.newArrayList();
        for (int i = 0; i < 4; i++) {
            chatNews.add(new ChatNew(BigDecimal.valueOf(i), i + 1, i + 2));
        }
        chatNews.add(new ChatNew(null, null, null));
        return chatNews;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Paging<T> {
    private long total;
    private List<T> records;
    private long pageIndex;
    private long pageSize;
}

@Data
class IPage<T> {
    private long total;
    private List<T> records;
    private long current;
    private long size;
}

@Data
class LoginMessage {
    String typename;
    String accounttime;
}

