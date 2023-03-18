package com.fuchuang.A33;

import com.fuchuang.A33.DTO.WeeksDTO;
import com.fuchuang.A33.service.Impl.LocationServiceImpl;
import com.fuchuang.A33.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private LocationServiceImpl locationService ;

    @Test
    void test1() {
        Result result = locationService.getThreeMonthes("2023-03-18");
        ArrayList<WeeksDTO> data =(ArrayList<WeeksDTO>) result.getData();
        for (WeeksDTO datum : data) {
            System.out.println(datum);
        }
    }

}
