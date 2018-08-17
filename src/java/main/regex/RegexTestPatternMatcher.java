package java.main.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fj.F;
import fj.data.List;
import fj.data.Option;

public class RegexTestPatternMatcher {
  public static final String EXAMPLE_TEST = "This is my small example string which I'm going to use for pattern matching.";

  public static void main(String[] args) {
    Pattern pattern = Pattern.compile("(\\w+)");
    // in case you would like to ignore case sensitivity,
    // you could use this statement:
    // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(EXAMPLE_TEST);
    // check all occurance
    while (matcher.find()) {
      System.out.print("Start index: " + matcher.start());
      System.out.print(" End index: " + matcher.end() + " ");
      System.out.println(matcher.group());
    }
    // now create a new pattern and matcher to replace whitespace with tabs
    Pattern replace = Pattern.compile("\\s+");
    Matcher matcher2 = replace.matcher(EXAMPLE_TEST);
    System.out.println(matcher2.replaceAll("\t"));
    
    
	F<String,F<Integer, F<String, Option<String>>>> regexFunction = string -> group -> regex -> {
		Matcher match = Pattern.compile(regex).matcher(string);
		return match.find() ? Option.fromString(match.group(group)) : Option.none();
	};
    String test= "第1-8 ,  ,10-12周";
    List<String> listTest = List.arrayList(test);
    List<String> list = List.arrayList(test.split("[,\\s]")).filter(s->!s.equals(""));
    list.forEach(s -> System.out.println(s));
    
//    listTest.map(s -> {
//		Matcher mtc = Pattern.compile("第(.+)周").matcher(s);
//		return mtc.find() ? mtc.group(1) : ""; 
//	})
//	.bind(s -> List.arrayList(s.split("[,\\s]")))
//	.filter(s -> !s.equals(""))
////	.filter(s -> s.contains("-"))
//	.bind(s -> s.contains("-") ? List.range(Integer.parseInt(regexFunction.f(s).f(1).f("(\\d+)-(\\d+)").some()), Integer.parseInt(regexFunction.f(s).f(2).f("(\\d*)-(\\d*)").some())+1).map(i -> String.valueOf(i)) : List.arrayList(s))
//	.forEach(System.out::println);

  }
} 