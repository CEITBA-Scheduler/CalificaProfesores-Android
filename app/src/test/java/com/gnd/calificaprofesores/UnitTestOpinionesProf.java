package com.gnd.calificaprofesores;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTestOpinionesProf {
    private FirebaseAuth mAuth;

    @Test
    public void makeVote() throws Exception {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("newtonis.penguin@gmail.com","lala").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    MakeAction();
                }else{
                    System.out.print("Error: coudln't log in");
                }
            }
        });
    }
    public void MakeAction(){

    }
}