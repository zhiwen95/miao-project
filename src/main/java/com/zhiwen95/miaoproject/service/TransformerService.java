package com.zhiwen95.miaoproject.service;

import com.zhiwen95.miaoproject.entity.TransformerConfig;
import java.io.File;
import java.util.*;

import com.zhiwen95.miaoproject.entity.TransformerRule;
import com.zhiwen95.miaoproject.util.ScriptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransformerService {
  public void transform(TransformerConfig transformerConfig) {
    Map<String, Collection<File>> files = new HashMap<>();
    for (String sourceDirectory : transformerConfig.getSourceDirectories()) {
      final File directory = new File(sourceDirectory);
      if (!directory.exists()) {
        continue;
      }
      final Collection<File> listFiles =
          FileUtils.listFiles(
              directory,
              FileFilterUtils.fileFileFilter().and(FileFilterUtils.suffixFileFilter("mp4")),
              FileFilterUtils.directoryFileFilter());
      files.put(directory.getAbsolutePath(), listFiles);
    }
    for (Map.Entry<String, Collection<File>> entry : files.entrySet()) {
      for (File file : entry.getValue()) {
        for (TransformerRule transformerRule : transformerConfig.getTransformerRules()) {
          final HashMap<String, Object> env = new HashMap<>();
          final String fileName = file.getName();
          env.put("fileName", fileName);
          env.put("fileBaseName", FilenameUtils.getBaseName(fileName));
          env.put("fileExtension", FilenameUtils.getExtension(fileName));
          final boolean accept = (boolean) ScriptUtils.execute(transformerRule.getCondition(), env);
          if(accept) {

          }
        }
      }
    }
  }
}
