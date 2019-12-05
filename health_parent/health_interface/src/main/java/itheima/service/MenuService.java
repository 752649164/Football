package itheima.service;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Map> findByUsername(String username);
}
