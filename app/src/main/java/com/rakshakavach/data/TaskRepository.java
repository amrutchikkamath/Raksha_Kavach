package com.rakshakavach.data;

import com.rakshakavach.model.QuizQuestion;
import com.rakshakavach.model.SafetyGear;
import com.rakshakavach.model.WorkTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskRepository {

    public static List<WorkTask> getAllTasks() {
        List<WorkTask> tasks = new ArrayList<>();

        // Welding
        tasks.add(new WorkTask("welding", "Welding", "🔧", "HIGH", 85,
                Arrays.asList(
                        new SafetyGear("helmet", "Welding Helmet", "🪖", "Protects face from arc flash", "Severe eye damage, blindness"),
                        new SafetyGear("gloves", "Welding Gloves", "🧤", "Heat-resistant leather gloves", "Burns, electric shock"),
                        new SafetyGear("boots", "Safety Boots", "👢", "Steel-toe boots with non-slip sole", "Foot crushing, electric shock"),
                        new SafetyGear("apron", "Welding Apron", "🦺", "Flame-resistant leather apron", "Burns from sparks and spatter"),
                        new SafetyGear("earplugs", "Earplugs", "🔇", "Protects from arc noise", "Hearing loss over time")
                ),
                Arrays.asList("Arc eye (photokeratitis)", "Severe burns", "Electric shock", "Hearing damage", "Metal fume fever")
        ));

        // Height Work
        tasks.add(new WorkTask("height_work", "Height Work", "🏗️", "CRITICAL", 95,
                Arrays.asList(
                        new SafetyGear("harness", "Safety Harness", "🦺", "Full-body fall arrest harness", "Fatal fall injury"),
                        new SafetyGear("helmet", "Hard Hat", "🪖", "Protects from falling objects", "Head injury, death"),
                        new SafetyGear("boots", "Non-Slip Boots", "👢", "Boots with grip sole", "Slipping, falling"),
                        new SafetyGear("gloves", "Work Gloves", "🧤", "Grip gloves for stability", "Loss of grip, fall")
                ),
                Arrays.asList("Fatal falls", "Fractures", "Head trauma", "Internal injuries")
        ));

        // Digging Trench
        tasks.add(new WorkTask("digging", "Digging Trench", "⛏️", "MEDIUM", 65,
                Arrays.asList(
                        new SafetyGear("helmet", "Hard Hat", "🪖", "Protects from falling soil", "Head injury"),
                        new SafetyGear("boots", "Safety Boots", "👢", "Protects feet from digging tools", "Foot injury"),
                        new SafetyGear("gloves", "Work Gloves", "🧤", "Prevents blisters and cuts", "Hand injuries"),
                        new SafetyGear("vest", "Hi-Vis Vest", "🦺", "Makes you visible to machinery", "Being hit by vehicles")
                ),
                Arrays.asList("Trench collapse", "Tool injuries", "Being struck by machinery", "Muscle strains")
        ));

        // Chemical Handling
        tasks.add(new WorkTask("chemicals", "Chemical Handling", "⚗️", "CRITICAL", 90,
                Arrays.asList(
                        new SafetyGear("mask", "Respirator Mask", "😷", "Filters toxic fumes", "Lung damage, poisoning"),
                        new SafetyGear("goggles", "Safety Goggles", "🥽", "Full seal chemical splash protection", "Chemical burns to eyes"),
                        new SafetyGear("gloves", "Chemical Gloves", "🧤", "Nitrile/rubber gloves", "Skin burns, absorption"),
                        new SafetyGear("apron", "Chemical Apron", "🦺", "Chemical-resistant apron", "Skin exposure"),
                        new SafetyGear("boots", "Rubber Boots", "👢", "Chemical-resistant footwear", "Foot chemical burns")
                ),
                Arrays.asList("Chemical burns", "Lung damage", "Poisoning", "Eye damage", "Skin disease")
        ));

        // Electrical Work
        tasks.add(new WorkTask("electrical", "Electrical Work", "⚡", "CRITICAL", 92,
                Arrays.asList(
                        new SafetyGear("gloves", "Insulated Gloves", "🧤", "High-voltage rated rubber gloves", "Electrocution, burns"),
                        new SafetyGear("boots", "Insulated Boots", "👢", "Rubber-soled boots", "Electric shock"),
                        new SafetyGear("helmet", "Insulated Helmet", "🪖", "Arc-rated hard hat", "Electrical arc flash"),
                        new SafetyGear("goggles", "Arc Flash Goggles", "🥽", "Arc-rated eye protection", "Eye burns"),
                        new SafetyGear("suit", "Arc Flash Suit", "🦺", "Full arc flash protection", "Severe burns")
                ),
                Arrays.asList("Electrocution", "Arc flash burns", "Electric shock", "Cardiac arrest", "Blast injury")
        ));

        // Lifting/Loading
        tasks.add(new WorkTask("lifting", "Heavy Lifting", "📦", "MEDIUM", 60,
                Arrays.asList(
                        new SafetyGear("belt", "Back Support Belt", "🦺", "Lumbar support for heavy lifting", "Back injury, hernia"),
                        new SafetyGear("gloves", "Work Gloves", "🧤", "Grip gloves for handling", "Hand cuts, crushing"),
                        new SafetyGear("boots", "Safety Boots", "👢", "Steel-toe boots", "Dropped load foot injury"),
                        new SafetyGear("helmet", "Hard Hat", "🪖", "Protection from overhead loads", "Head injury")
                ),
                Arrays.asList("Back injury", "Hernia", "Crushed feet", "Muscle tears", "Joint damage")
        ));

        // Grinding
        tasks.add(new WorkTask("grinding", "Grinding/Cutting", "🔩", "HIGH", 80,
                Arrays.asList(
                        new SafetyGear("goggles", "Face Shield", "🥽", "Full face grinder shield", "Eye penetration from sparks"),
                        new SafetyGear("gloves", "Anti-Vibration Gloves", "🧤", "Protects hands from vibration", "Hand-arm vibration syndrome"),
                        new SafetyGear("boots", "Safety Boots", "👢", "Protects from disc fragments", "Foot lacerations"),
                        new SafetyGear("earplugs", "Earplugs", "🔇", "Noise protection", "Permanent hearing loss"),
                        new SafetyGear("mask", "Dust Mask", "😷", "Filters metal dust", "Lung disease")
                ),
                Arrays.asList("Eye penetration from sparks", "Hand lacerations", "Hearing loss", "Disc burst injuries", "Lung disease from dust")
        ));

        // Painting
        tasks.add(new WorkTask("painting", "Spray Painting", "🎨", "MEDIUM", 55,
                Arrays.asList(
                        new SafetyGear("mask", "Respirator", "😷", "Filters paint fumes", "Solvent poisoning"),
                        new SafetyGear("goggles", "Safety Goggles", "🥽", "Prevents eye splash", "Chemical eye burns"),
                        new SafetyGear("gloves", "Work Gloves", "🧤", "Prevents skin contact", "Dermatitis, absorption"),
                        new SafetyGear("suit", "Coveralls", "🦺", "Full body paint protection", "Skin rash, chemical burn")
                ),
                Arrays.asList("Solvent inhalation", "Eye injuries", "Skin disorders", "Flammability risk")
        ));

        return tasks;
    }

    public static WorkTask getTaskById(String id) {
        for (WorkTask task : getAllTasks()) {
            if (task.getId().equals(id)) return task;
        }
        return null;
    }

    public static List<QuizQuestion> getDailyQuestions() {
        List<QuizQuestion> questions = new ArrayList<>();

        questions.add(new QuizQuestion(
                "What should you do FIRST before starting any construction task?",
                new String[]{"Start working immediately", "Complete the safety gear checklist", "Wait for supervisor", "Check your phone"},
                1,
                "Always complete the safety gear checklist before beginning work to ensure all PPE is worn."
        ));

        questions.add(new QuizQuestion(
                "What does PPE stand for?",
                new String[]{"Personal Protective Equipment", "Public Protection Enforcement", "Plant Personnel Entry", "Primary Prevention Element"},
                0,
                "PPE stands for Personal Protective Equipment - the gear that protects workers from hazards."
        ));

        questions.add(new QuizQuestion(
                "If you witness a near miss incident, you should:",
                new String[]{"Ignore it if no one was hurt", "Report it immediately in the incident log", "Tell only your friends", "Wait until end of shift"},
                1,
                "Always report near misses immediately. They are warning signs that prevent future accidents."
        ));

        questions.add(new QuizQuestion(
                "When working at heights above 2 meters, which gear is MANDATORY?",
                new String[]{"Only a helmet", "Only a harness", "Full body harness AND helmet", "Just good boots"},
                2,
                "At heights above 2 meters, both a full-body safety harness and helmet are mandatory to prevent fatal falls."
        ));

        questions.add(new QuizQuestion(
                "How should you lift a heavy object safely?",
                new String[]{"Bend your back and pull", "Bend knees, keep back straight, lift with legs", "Twist your body while lifting", "Lift as fast as possible"},
                1,
                "Always bend your knees, keep your back straight, and use leg muscles to lift. This prevents back injuries."
        ));

        questions.add(new QuizQuestion(
                "What color does a HIGH VISIBILITY vest typically come in?",
                new String[]{"Blue or purple", "Orange/Yellow with reflective strips", "Red only", "Any bright color"},
                1,
                "Hi-vis vests are orange or yellow with silver reflective strips so workers are visible to machinery operators."
        ));

        questions.add(new QuizQuestion(
                "Which of the following is NOT a safe practice while using a grinder?",
                new String[]{"Wearing a face shield", "Checking the disc for cracks before use", "Removing safety guards for better access", "Securing workpiece firmly"},
                2,
                "NEVER remove safety guards from grinders. They protect you from disc fragments and sparks."
        ));

        questions.add(new QuizQuestion(
                "What should you do if your safety helmet has a crack?",
                new String[]{"Continue using it, it's probably fine", "Cover the crack with tape", "Replace it immediately", "Ask someone to assess it later"},
                2,
                "A cracked helmet provides NO protection. Replace it immediately - your life depends on proper equipment."
        ));

        questions.add(new QuizQuestion(
                "Silica dust from cutting concrete can cause:",
                new String[]{"Minor skin irritation only", "Silicosis - a deadly lung disease", "Temporary headache", "No serious harm"},
                1,
                "Silica dust causes silicosis, an incurable and potentially fatal lung disease. Always wear a proper respirator."
        ));

        questions.add(new QuizQuestion(
                "What does a Safety Score reward in Raksha-Kavach?",
                new String[]{"Working the most hours", "Completing consecutive safe days without accidents", "Reporting the most incidents", "Using the most PPE"},
                1,
                "Your Safety Score increases with each safe consecutive day - it rewards consistent safe behavior!"
        ));

        return questions;
    }
}
