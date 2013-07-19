package org.apache.uima.examples.cpe;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//import org.apache.uima.jcas.tcas.Annotation;

public class QuestionFactory {
	Boolean bloodflag = false;
	Boolean alcoholflag = false;
	Boolean sleepinghourflag = false;
	FileWriter fileWriter;
	Boolean bodyfatflag = false;
	String aText;
	ArrayList<String> questionlist = new ArrayList<String>();
	int featurenumber = 0;
	int Excellentnumber = 0;
	int Goodnumber = 0;
	int Fairnumber = 0;
	int Poornumber = 0;

	// those are the features used to determine questions
	int bodyfatrate = 0;
	int feet = 0;
	double weight = 0;
	double lbs=0;
	int inches = 0;
	double BMI = 0;
	int age = 0;
	int bloodlowpressure = 0;
	int blooduppressure = 0;
	int alcohollevel = 0;
	int diabetes = 0;
	int Cancerawareness = 0;
	int stress = 0;
	int sleepinghours = 0;
	int sleepingstatus = 0;
	int coronarylevel = 0;
	int smokinglevel = 0;
	int cancerlevel = 0;
	final int BAD = 2;
	final int OKAY = 1;
	HashMap<String, String> evidence = new HashMap<String, String>();
	HashMap<String, ArrayList<String>> newquestionlist = new HashMap<String, ArrayList<String>>();

	QuestionFactory() {

	}

	QuestionFactory(FileWriter filewriter) {
		fileWriter = filewriter;

		// shangqingCVSconsummer cvsconsumer=new shangqingCVSconsummer();
		// cvsconsumer.writeCsv();

	}

	void process(String annottype, String text) throws IOException {

		if (annottype.contains("OneFeatureSentence2")) {
			// Adding evidence for each body feature
			text = text.toLowerCase();

			if (text.contains("height")) {
				evidence.put("height", text);
			}
			if (text.contains("weight")) {
				evidence.put("weight", text);
			}
			if (text.contains("body fat")) {

				evidence.put("bodyfatrate", text);
			}
			if (text.contains("age")) {

				evidence.put("age", text);
			}
			if (text.contains("smok")) {
				evidence.put("smoke", text);
			}
			if (text.contains("blood pressure")) {
				evidence.put("bloodpressure", text);
			}
			if (text.contains("stress")) {
				evidence.put("stress", text);
			}
			if (text.contains("alcohol")) {
				evidence.put("alcohol", text);
			}
			if (text.contains("sleep")) {
				evidence.put("sleep", text);
			}
			if (text.contains("coronary")) {
				evidence.put("coronary", text);
			}
			if (text.contains("diabetes")) {
				evidence.put("diabetes", text);
			}
			if (text.contains("cancer")) {
				evidence.put("cancer", text);
			}
			// fileWriter.write(annottype + " " +text +"\n");
			featurenumber++;
			if (text.contains("excellent")) {
				Excellentnumber++;
			}
			if (text.contains("good")) {
				Goodnumber++;
			}
			if (text.contains("fair")) {
				Fairnumber++;
			}
			if (text.contains("poor")) {
				Poornumber++;
			}
		}
		if (annottype.contains("upblood")) {
			bloodflag = true;
			blooduppressure = Integer.parseInt(text);
		}
		if (annottype.contains("coronarystatus")) {
			text = text.toLowerCase();
			if (text.contains("poor") || (text.contains("fair"))) {
				coronarylevel = 2;
			} else {
				coronarylevel = 1;
			}
		}
		if (annottype.contains("lowblood")) {
			bloodflag = true;
			bloodlowpressure = Integer.parseInt(text);
		}
		if (annottype.contains("Diabetes")) {
			text = text.toLowerCase();
			System.out.println(text);
			if (text.contains("yes")) {
				diabetes = BAD;
			} else {
				diabetes = OKAY;
			}
		}

		// this is used to fetch fate rate data
		if (annottype.contains("bodyrate")) {
			bodyfatflag = true;
			String tmp = text.substring(0, text.length() - 1);
			bodyfatrate = Integer.parseInt(tmp);
		}
		if (annottype.contains("feet")) {
			feet = Integer.parseInt(text);
		}
		if (annottype.contains("inches")) {
			inches = Integer.parseInt(text);
		}
		if (annottype.contains("weight")) {
			lbs = Double.valueOf(text).doubleValue();
			weight = lbs * 0.45359;
		}
		if (annottype.contains("cancerawareness")) {
			text = text.toLowerCase();
			if (text.contains("poor") || (text.contains("fair"))) {
				cancerlevel = BAD;
			} else {
				cancerlevel = OKAY;
			}
		}
		if (annottype.contains("smokingstandard")) {
			text = text.toLowerCase();
			if (text.contains("poor") || (text.contains("fair"))) {
				smokinglevel = BAD;
			} else {
				smokinglevel = OKAY;
			}
		}
		if (annottype.contains("age")) {
			age = Integer.parseInt(text);
		}
		if (annottype.contains("alcohol")) {
			alcoholflag = true;
			text = text.toLowerCase();
			if (text.contains("poor") || (text.contains("fair"))) {
				alcohollevel = BAD;
			} else {
				alcohollevel = OKAY;
			}
		}
		if (annottype.contains("sleepingstatus")) {
			sleepinghourflag = true;
			text = text.toLowerCase();

			if (text.equals("poor") || text.equals("fair")) {

				sleepingstatus = BAD;
			}
		}
		if (annottype.contains("stressstandard")) {
			text = text.toLowerCase();
			if (text.equals("excellent"))
				stress = OKAY;
			if (text.equals("good"))
				stress = OKAY;
			if (text.equals("fair"))
				stress = BAD;
			if (text.equals("poor"))
				stress = BAD;
		}

	}

	void questiongeneration() {

		double height = (feet * 30.48 + inches * 2.54) / 100;
		BMI = weight / (height * height);
		if(true){
			ArrayList<String> evidencebag = new ArrayList<String>();
			String question = null;
			question="How much water should a "+feet+" feet "+inches +" inches person drink everyday?";
			newquestionlist.put(question, evidencebag);
			String question2 = "How many minutes of exercises should a person at age "+age+ " take everyday?";
			newquestionlist.put(question2, evidencebag);
			String question3="How much calories of exercises should a person take to reduce 2 pounds a week?";
			newquestionlist.put(question3, evidencebag);
			
		}
		
		
		
		if (BMI > 25 || bodyfatrate > 25) {
			ArrayList<String> evidencebag = new ArrayList<String>();
			String question = null;
			if (coronarylevel < BAD) {
				question = "What kinds of physical activity can reduce body weight quickly?";

				evidencebag.add(evidence.get("weight"));
				evidencebag.add(evidence.get("height"));
				evidencebag.add("So your BMI is about " + BMI);
				evidencebag.add(evidence.get("bodyfatrate"));
			} else {
				question = "What kind of moderate or aerobic exercise can reduce body weight? ";
				evidencebag.add(evidence.get("weight"));
				evidencebag.add(evidence.get("height"));
				evidencebag.add("So your BMI is about " + BMI);
				evidencebag.add(evidence.get("bodyfatrate"));
				evidencebag.add(evidence.get("coronary"));
			}
			newquestionlist.put(question, evidencebag);

		}
		if (true) {
			ArrayList<String> evidencebag = new ArrayList<String>();
			String question = null;
			question = "How much calories I need to take everyday to keep my weight if I am "+feet+" feet "+inches+" inches and "+lbs+" lbs?";

			evidencebag.add(evidence.get("weight"));
			evidencebag.add(evidence.get("height"));
			newquestionlist.put(question, evidencebag);

		}
		if(cancerlevel==BAD){
			
			if(smokinglevel==BAD){
				ArrayList<String> evidencebag = new ArrayList<String>();
				String question="What's the correlation between cancer with heavy smoke?";
				evidencebag.add(evidence.get("cancer"));
				evidencebag.add(evidence.get("smoke"));
				newquestionlist.put(question, evidencebag);
			}if(alcohollevel==BAD){
				ArrayList<String> evidencebag = new ArrayList<String>();
				String question="What's the correlation between cancer with heavy drink habbit?";
				evidencebag.add(evidence.get("cancer"));
				evidencebag.add(evidence.get("alcohol"));
				newquestionlist.put(question, evidencebag);
			}if(sleepingstatus==BAD){
				ArrayList<String> evidencebag = new ArrayList<String>();
				String question="What's the correlation between cancer with lack of sleep?";
				evidencebag.add(evidence.get("cancer"));
				evidencebag.add(evidence.get("sleep"));
				newquestionlist.put(question, evidencebag);
			}if(coronarylevel==BAD){
				ArrayList<String> evidencebag = new ArrayList<String>();
				String question="Will coronary risk increases the chances of getting  cancer?";
				evidencebag.add(evidence.get("cancer"));
				evidencebag.add(evidence.get("coronary"));
				newquestionlist.put(question, evidencebag);
			}
			
		}

		if (age > 0) {
			String question = "";
			ArrayList<String> evidencebag = new ArrayList<String>();
			if (age < 20) {
				question = "what kinds of activities are most popular among teenagers?";
			} else if (age >= 20 && age < 50) {
				question = "what kinds of activities are suitable for mid-age people?";
			} else {
				question = "what kinds of physical activity is good for elderly people?";
			}
			evidencebag.add(evidence.get("age"));
			newquestionlist.put(question, evidencebag);
		}
		if (diabetes == BAD) {
			String question = "";
			ArrayList<String> evidencebag = new ArrayList<String>();
			question = "What kinds of physical activity should diabetes patients avoid?";
			evidencebag.add(evidence.get("diabetes"));
			newquestionlist.put(question, evidencebag);
		}if (diabetes == OKAY) {
			String question = "";
			ArrayList<String> evidencebag = new ArrayList<String>();
			question = "What is the statistical possibility to get diabetes if my BMI is "+BMI +"?";
			evidencebag.add(evidence.get("diabetes"));
			newquestionlist.put(question, evidencebag);
		}
		
		if (diabetes == OKAY && BMI > 28 && sleepingstatus == BAD
				&& bodyfatrate > 30) {
			String question = "";
			String question2 = "";
			ArrayList<String> evidencebag = new ArrayList<String>();
			question = "What lifestyle changes can prevent diabetes?";
			
			evidencebag.add(evidence.get("bodyrate"));
			evidencebag.add(evidence.get("height"));
			evidencebag.add(evidence.get("weight"));
			evidencebag.add(evidence.get("diabetes"));
			newquestionlist.put(question, evidencebag);
			
		}
		if (smokinglevel == BAD && sleepingstatus == BAD) {
			String question = "Can smoking cause trouble sleeping?";
			ArrayList<String> evidencebag = new ArrayList<String>();
			evidencebag.add(evidence.get("smoke"));
			evidencebag.add(evidence.get("sleep"));
			newquestionlist.put(question, evidencebag);
		}
		
		
		if (bloodlowpressure > 70 || blooduppressure > 140) {
			String question = "What moderate acitivties can solve high blood pressure?";
			ArrayList<String> evidencebag = new ArrayList<String>();
			evidencebag.add(evidence.get("bloodpressure"));
			newquestionlist.put(question, evidencebag);
		}
		if (bloodlowpressure < 50 || blooduppressure < 110) {
			String question = "What moderate activities can solve low blood pressure?";
			ArrayList<String> evidencebag = new ArrayList<String>();
			evidencebag.add(evidence.get("bloodpressure"));
			newquestionlist.put(question, evidencebag);
		}
		if (sleepingstatus == BAD) {
			String question = "What physical activity can improve sleep?";
			ArrayList<String> evidencebag = new ArrayList<String>();
			evidencebag.add(evidence.get("sleep"));
			newquestionlist.put(question, evidencebag);

			if (alcohollevel < BAD) {
				question = "What kinds of drinks could mitigate insomnia?";
				ArrayList<String> evidencebag2 = new ArrayList<String>();
				evidencebag2.add(evidence.get("sleep"));

				evidencebag2.add(evidence.get("alcohol"));
				newquestionlist.put(question, evidencebag2);

			}
		}
		// question related to stress
		if (stress > 2) {
			String question = "";
			ArrayList<String> evidencebag = new ArrayList<String>();
			if (sleepingstatus == BAD) {
				question = "What exercise can relieve stress and improve sleep?";
				evidencebag.add(evidence.get("sleep"));
				String question2="What's the correlation between sleeping and stress?";
				newquestionlist.put(question2, evidencebag);
			} else {
				question = "What kinds of physical activities can relieve stress?";
			}
			evidencebag.add(evidence.get("stress"));
			newquestionlist.put(question, evidencebag);
		}
	}

	void print() {
		String general = featurenumber
				+ " body condition features are analyzed \n";
		String excellent = Excellentnumber + " features are Excellent \n";
		String good = Goodnumber + " features are Good \n";
		String fair = Fairnumber + " features are Fair \n";
		String poor = Poornumber + " features are Poor \n";
		try {
			fileWriter.write("\n\n");
			fileWriter.write(general);
			fileWriter.write(excellent);
			fileWriter.write(good);
			fileWriter.write(fair);
			fileWriter.write(poor);
			fileWriter.write("\n\n");
			Iterator iterator = newquestionlist.keySet().iterator();
			int j=1;
			while (iterator.hasNext()) {
				String tmpstring = (String) iterator.next();
				fileWriter.write(j+":  ");
				fileWriter.write(tmpstring + "\n");
				ArrayList<String> tmp = newquestionlist.get(tmpstring);
				for (int i = 0; i < tmp.size(); i++) {
					if (tmp.get(i) != "") {
						fileWriter.write("Evidence:  " + tmp.get(i) + " \n");
					}
				}
				fileWriter.write("\n");
				j++;

			}
			fileWriter.write("\n\n\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < questionlist.size(); i++) {
			try {
				fileWriter.write(questionlist.get(i));
				fileWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
