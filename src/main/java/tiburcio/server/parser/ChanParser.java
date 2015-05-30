package tiburcio.server.parser;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Qualifier;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

public class ChanParser {
  private static Set<String> NOUNTYPES = Sets.newHashSet("NN", "N", "PRP");
  private static final Logger log = Logger.getLogger(ChanParser.class.getSimpleName());
  private final Parser parser;

  @Inject
  public ChanParser(@ParseModelFile File parserModelFile) {
    try {
      ParserModel model = new ParserModel(new FileInputStream(
          parserModelFile.getAbsolutePath()));
      this.parser = ParserFactory.create(model);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public String extractNoun(String sentence) {
    if (sentence.isEmpty()) {
      return "";
    }
    for (Parse parse : ParserTool.parseLine(sentence, parser, 1)) {
      parse.show();
    }
    List<String> possibleNouns = nounsToString(getNouns(ParserTool
        .parseLine(sentence, parser, 1)[0]));
    log.info("Possible nouns: " + possibleNouns);
    if (possibleNouns.isEmpty()) {
      return "";
    }
    int possibleNounIdx = new Random().nextInt(possibleNouns.size());
    log.info("Possible Noun IDX: " + possibleNounIdx);
    return possibleNouns.get(possibleNounIdx);
  }

  public static List<String> nounsToString(List<Parse> nounPhrases) {
    ImmutableList.Builder<String> builder = ImmutableList.<String>builder();
    for (Parse parse : nounPhrases) {
      builder.add(parse.toString());
    }
    return builder.build();
  }

  public static List<Parse> getNouns(Parse p) {
    List<Parse> nouns = new ArrayList<Parse>();
    String type = p.getType();
    if (NOUNTYPES.contains(type)) {
      nouns.add(p);
    }
    for (Parse child : p.getChildren()) {
      nouns.addAll(getNouns(child));
    }
    return nouns;
  }
  
  @Target({PARAMETER})
  @Retention(RUNTIME)
  @Qualifier
  public @interface ParseModelFile {}
}
