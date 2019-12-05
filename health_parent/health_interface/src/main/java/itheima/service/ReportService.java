package itheima.service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Integer> getMemberReport(List<String> list);

    Map<String, Object> getBusinessReportData() throws Exception;
}
