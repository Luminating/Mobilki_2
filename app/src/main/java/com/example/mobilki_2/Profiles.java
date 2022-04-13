package com.example.mobilki_2;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Profiles {
    private static Profiles instance;
    private ArrayList<Profile> profileList = new ArrayList<>();
    private static final String fileName = "profiles.out";
    private static int currentProfileIndex = 0;

    public static synchronized Profiles getInstance(){
        if (instance == null) {
            instance = new Profiles();
        }
        return instance;
    }

    private Profiles(){

    }

    public void serializeObjects(Context context){
        try {
            FileOutputStream fileOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(instance.profileList);
            out.writeObject(currentProfileIndex);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void deserializeObjects(Context context){
        try {
            FileInputStream fileIn = context.openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //int a = (int) in.readObject();
            instance.profileList = (ArrayList<Profile>)in.readObject();
            currentProfileIndex = (int) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (instance.profileList.size() == 0){
            instance.profileList.add(new Profile("no name","",null));
        }
    }

    public ArrayList<Profile> getProfileList() {
        return profileList;
    }

    public void addProfile(String profileName) {

        profileList.add(new Profile(profileName, "", null));
        currentProfileIndex = profileList.size() - 1;
    }

    public boolean isProfileNamePresent(String name) {
        boolean result = false;
        for (Profile profile: profileList){
            if (profile.getName().equals(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isProfileNamePresentAndNotCurrent(String name) {
        boolean result = false;
        for (Profile profile: profileList){
            if (profile.getName().equals(name) && (!profile.equals(profileList.get(currentProfileIndex)))) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean changeProfile(String name){
        boolean result = false;
        for (int index = 0; index < profileList.size(); index++){
            if (profileList.get(index).getName().equals(name)) {
                currentProfileIndex = index;
                result = true;
                break;
            }
        }
        return result;
    }

    public void editCurrentProfile(String name){
        profileList.get(currentProfileIndex).setName(name);
    }

    public boolean deleteProfile(String name){
        boolean result = false;
        Profile currentProfile = getCurrentProfile();
        Iterator<Profile> iterator = profileList.iterator();
        while (iterator.hasNext()){
            String profileName = iterator.next().getName();
            if (profileName.equals(name)){
                result = true;
                iterator.remove();
                break;
            }
        }
        currentProfileIndex = profileList.indexOf(currentProfile);
        return result;
    }

    public Profile getCurrentProfile(){
        return (profileList.size() != 0) ? profileList.get(currentProfileIndex): null;
    }

}

