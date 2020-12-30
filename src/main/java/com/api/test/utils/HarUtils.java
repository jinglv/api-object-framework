package com.api.test.utils;

import com.api.test.api.ApiActionModel;
import com.api.test.api.ApiObjectModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * har文件解读生成测试用例
 *
 * @author jingLv
 * @date 2020/12/30
 */
public class HarUtils {

    private HarUtils() {
    }

    /**
     * 根据har文件生成测试用例
     *
     * @param filePath har文件的路径
     * @param fileName 生成case的名称
     * @throws HarReaderException 抛出异常
     * @throws IOException        抛出异常
     */
    public static void generationCase(String filePath, String fileName) throws HarReaderException, IOException {
        HarReader harReader = new HarReader();
        Har har = harReader.readFromFile(new File(filePath));

        ApiObjectModel apiObjectModel = new ApiObjectModel();
        ApiActionModel apiActionModel = new ApiActionModel();
        HashMap<String, ApiActionModel> actions = new HashMap<>();
        HashMap<String, String> queryMap = new HashMap<>();
        har.getLog().getEntries().forEach(entries -> {
            HarRequest harRequest = entries.getRequest();
            harRequest.getQueryString().forEach(query -> {
                queryMap.put(query.getName(), query.getValue());
            });
            String method = harRequest.getMethod().toString();
            String url = harRequest.getUrl();
            apiActionModel.setQuery(queryMap);
            apiActionModel.setMethod(method);
            apiActionModel.setUrl(url);
        });
        apiObjectModel.setName(fileName);
        apiObjectModel.setActions(actions);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("src/main/resources/har/" + fileName + ".yaml"), apiObjectModel);
    }
}
