package com.doinWondrs.betterme.helpers;


import com.doinWondrs.betterme.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TypesOfWorkouts {

    public HashMap<String, String> workoutRoutine;
    private int gif1, gif2, gif3;

    //Constructor
    public TypesOfWorkouts(String pWType)
    {
        this.workoutRoutine = new HashMap<>();
        buildWorkout(pWType);
    }





    public void abdominalWorkouts(){
        gif1 = R.drawable.ab1;
        gif2 = R.drawable.ab2;
        gif3 = R.drawable.ab3;
        String gifToString1 = String.valueOf(gif1);
        String gifToString2 = String.valueOf(gif2);
        String gifToString3 = String.valueOf(gif3);

        workoutRoutine.put("leg over crunch","one leg over knee and crunch,abs," + gifToString1);
        workoutRoutine.put("machine ab twist","twist body and toward toes,abs,"+gifToString2);
        workoutRoutine.put("crunches", "lean towards toes,abs," + gifToString3);
    }

    public void chestWorkouts(){

        gif1 = R.drawable.chest1;
        gif2 = R.drawable.chest2;
        gif3 = R.drawable.chest3;
        String gifToString1 = String.valueOf(gif1);
        String gifToString2 = String.valueOf(gif2);
        String gifToString3 = String.valueOf(gif3);

        //set exercise routine up [what this is: lifting , what muscles groups: legs, gif or image of workout]

        workoutRoutine.put("Machine Inner Chest Press", "press with machine,chest," + gifToString1);
        workoutRoutine.put("Bench Press","Barbell Bench press,chest," + gifToString2);
        workoutRoutine.put("Push Ups","Medicine Ball Supine Chest Throw,chest," + gifToString3);

    }

    public void LegWorkouts(){
        gif1 = R.drawable.legs1;
        gif2 = R.drawable.legs2;
        gif3 = R.drawable.legs3;
        String gifToString1 = String.valueOf(gif1);
        String gifToString2 = String.valueOf(gif2);
        String gifToString3 = String.valueOf(gif3);
        workoutRoutine.put("lever kneeling leg curl", "kick away and use machine,leg," + gifToString1);
        workoutRoutine.put("lever altenate leg press", "machine kcik off,leg,"+ gifToString2);
        workoutRoutine.put("lever hip extension","machine and kickback and away,leg," +gifToString3);
    }

    public void armWorkouts(){

        gif1 = R.drawable.arms1;
        gif2 = R.drawable.arms2;
        gif3 = R.drawable.arms3;
        String gifToString1 = String.valueOf(gif1);
        String gifToString2 = String.valueOf(gif2);
        String gifToString3 = String.valueOf(gif3);

        workoutRoutine.put("Lever overhand triceps dip","use machine and lean 20 degrees forward,triceps," + gifToString1);
        workoutRoutine.put("ring dips", "use rings to dip,tricep," + gifToString2);
        workoutRoutine.put("wrist roller","use a plate and string to rotate extended and roll back in,forarm," + gifToString3);
    }
    public void backWorkouts(){
        gif1 = R.drawable.back1;
        gif2 = R.drawable.back2;
        gif3 = R.drawable.back3;
        String gifToString1 = String.valueOf(gif1);
        String gifToString2 = String.valueOf(gif2);
        String gifToString3 = String.valueOf(gif3);

        workoutRoutine.put("bent over dumbell row","dumbells and lean forward,back," + gifToString1);
        workoutRoutine.put("chin-ups", "narrow grip,back," + gifToString2);
        workoutRoutine.put("planche","bodyweight,back," + gifToString3);

    }
    public void shoulderWorkouts(){
        gif1 = R.drawable.shoulder1;
        gif2 = R.drawable.shoulder2;
        gif3 = R.drawable.shoulder3;
        String gifToString1 = String.valueOf(gif1);
        String gifToString2 = String.valueOf(gif2);
        String gifToString3 = String.valueOf(gif3);

        workoutRoutine.put("seated barbell press","sit and press with barbell,shoulder," + gifToString1);
        workoutRoutine.put("barbell front raise","barbell raise parallel to the deck,barbell or dumbell," + gifToString2);
        workoutRoutine.put("dumbell front raise","dumbell and paralell to deck,shoulder," + gifToString3);

    }

    public void buildWorkout(String wType)//workout type string
    {
        //check string with TypesOfWorkouts class
        //if the are the same, then populate the WorkoutPageSecond.java with workouts
        switch (wType) {
            case "Abdominal Focus":
                abdominalWorkouts();
                break;
            case "Chest Focus":
                chestWorkouts();
                break;
            case "Legs Focus":
                LegWorkouts();
                break;
            case "Back Focus":
                backWorkouts();
                break;
            case "Arms Focus":
                armWorkouts();
                break;
            case "Shoulders Focus":
                shoulderWorkouts();
                break;

        }//END: swtich : types of workouts
    }//END:buildworkout

}//END: Class
