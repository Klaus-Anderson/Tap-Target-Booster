/**
 * @author Harry Cliff
 */
package angus.gaming.taptargetbooster.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Objects;

import angus.gaming.taptargetbooster.R;
import angus.gaming.taptargetbooster.activity.MainActivity;
import angus.gaming.taptargetbooster.utils.GameModel;
import angus.gaming.taptargetbooster.utils.GameType;
import angus.gaming.taptargetbooster.utils.ScoreModel;

@SuppressLint("ValidFragment")
public class GameFragment extends Fragment {


    private GameModel gameModel;
    private View rootView;
    private long muf;
    private double gameDurationMillis, targetDurationMillis, targetDpSize, targetsPerSecond, spawnType,
            triggerTime, dummyTrigger, hitTime, success, attempt, averageTotal, screenReset,
            xLocation, yLocation, hitDistance, scoreActual, quitTime = -1;
    private int targetPxSize, screenWidthDp, screenHeightDp, targetTopCorner, targetLeftCorner;
    private RelativeLayout gameFrame;
    private ImageView singleTarget, blackDot;
    private TextView timerText, distanceText, scoreTypeText, scoreText, targetsText, targetScoreText, countDownText;
    private boolean singleClicked, misclicked;

    public GameFragment(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_game, container, false);
        unpackGameSettings();
        setUpViews();
        setUpGameVariables();
        setUpCountDownTimer();

        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    private void setUpCountDownTimer() {
        new CountDownTimer((long) (gameDurationMillis + 3000), 10) {

            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished / 10));
                muf = (int) millisUntilFinished;
                updateGameState();
            }

            public void onFinish() {
                quitTime = 0;
                gameOver();
            }
        }.start();
    }

    private void setUpGameVariables() {
        misclicked = false;
        singleClicked = false;
        triggerTime = gameDurationMillis;
        dummyTrigger = 0;
        screenReset = 0;
        success = 0;
        attempt = 0;
        averageTotal = 0;
        scoreActual = 0;
    }

    private void setUpViews() {
        singleTarget = new ImageView(GameFragment.this.getActivity());
        singleTarget.setImageResource(R.drawable.target);
        singleTarget.setVisibility(View.INVISIBLE);
        singleTarget.setClickable(false);

        gameFrame = rootView.findViewById(R.id.gameFrame);
        gameFrame.setClickable(false);
        gameFrame.addView(singleTarget);

        blackDot = new ImageView(getActivity());
        blackDot.setImageResource(R.mipmap.blackdot);
        blackDot.setClickable(false);
        blackDot.setVisibility(View.INVISIBLE);
        gameFrame.addView(blackDot);

        distanceText = rootView.findViewById(R.id.distanceText);
        distanceText.setClickable(false);
        distanceText.setVisibility(View.INVISIBLE);

        countDownText = rootView.findViewById(R.id.cdText);
        countDownText.setClickable(false);

        timerText = rootView.findViewById(R.id.timerText);
        scoreTypeText = rootView.findViewById(R.id.scoreType);

        if (GameType.REACTION.equals(gameModel.getGameType()) ||
                GameType.TWITCH.equals(gameModel.getGameType())) {
            scoreTypeText.setText(R.string.sped);
        }
        scoreText = rootView.findViewById(R.id.score);
        targetsText = rootView.findViewById(R.id.targets);
        targetScoreText = rootView.findViewById(R.id.targetsScore);

        //Screen Click Listener
        gameFrame.setOnClickListener(v -> {
            //Time challenge uses onClickListener
            if ((GameType.TIME_CHALLENGE.equals(gameModel.getGameType()) ||
                    GameType.REACTION.equals(gameModel.getGameType())) &&
                    muf <= gameDurationMillis) {
                singleClicked = true;
                touchSetUp(targetLeftCorner + xLocation,
                        targetTopCorner + yLocation);
                blackDot.setVisibility(View.INVISIBLE);
            }
        });

        //Screen Touch Listener
        gameFrame.setOnTouchListener((v, event) -> {
            //Time challenge uses onClickListener
            v.performClick();
            if ((!GameType.TIME_CHALLENGE.equals(gameModel.getGameType()) &&
                    !GameType.REACTION.equals(gameModel.getGameType())) &&
                    gameFrame.isClickable()) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xLocation = event.getX();
                    yLocation = event.getY();
                }
                touchSetUp(xLocation, yLocation);

                if (gameFrame.isClickable()) {
                    //global variable clean-up
                    misclicked = true;
                    gameFrame.setClickable(false);
                    singleTarget.setClickable(false);
                    return true;
                } else
                    return false;
            } else
                return false;
        });

        singleTarget.setOnClickListener(v -> {
            //Time challenge uses onClickListener
            if ((GameType.TIME_CHALLENGE.equals(gameModel.getGameType()) ||
                    GameType.REACTION.equals(gameModel.getGameType())) &&
                    muf <= gameDurationMillis) {
                singleClicked = true;
                touchSetUp(targetLeftCorner + xLocation, targetTopCorner + yLocation);
                blackDot.setVisibility(View.INVISIBLE);
            }
        });
        //Target Touch Listener
        singleTarget.setOnTouchListener((v, event) -> {
            //Time challenge and Reaction uses onClickListener
            if ((!GameType.TIME_CHALLENGE.equals(gameModel.getGameType()) &&
                    !GameType.REACTION.equals(gameModel.getGameType())) &&
                    gameFrame.isClickable()) {
                //if onTouch trigger was a press
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //locations are relative to target
                    xLocation = event.getX();
                    yLocation = event.getY();
                }
                double distance = touchSetUp(targetLeftCorner + xLocation, targetTopCorner + yLocation);

                if (distance <= ((float)targetPxSize / 2) && singleTarget.isClickable()) {
                    singleClicked = true;
                    gameFrame.setClickable(false);
                    singleTarget.setClickable(false);
                    return true;
                } else {
                    misclicked = true;
                    gameFrame.setClickable(false);
                    singleTarget.setClickable(false);
                    return true;
                }
            }
            return false;
        });
    }

    private void unpackGameSettings() {
        targetDurationMillis = ((gameModel.getTargetDuration() + 1) / 10) * 100;
        gameDurationMillis = ((gameModel.getGameDuration() + 1) * (.5) + 14.5) * 1000;

        targetsPerSecond = (gameModel.getTargetsPerSecond() + 20) / 10;

        // 1 = random
        // 2 = instant
        // 0 = timed
        if (gameModel.getRandom())
            spawnType = 1;
        else if (gameModel.getRespawn())
            spawnType = 2;
        else
            spawnType = 0;
    }

    /**
     * called on a cdTimer onTick method
     * updates the Game State based user interaction
     */
    private void updateGameState() {

        //display settings
        targetDpSize = gameModel.getTargetSize();
        DisplayMetrics gameFrameMetrics = Objects.requireNonNull(getActivity()).
                getResources().getDisplayMetrics();
        screenWidthDp = gameFrame.getWidth();
        screenHeightDp = gameFrame.getHeight();
        targetPxSize = (int) Math.round(targetDpSize * (gameFrameMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        //1000 = 1 second
        double time = Double.parseDouble(String.valueOf(timerText.getText())) * 10;

        //screen reset = game begin
        if (time <= screenReset) {
            gameFrame.setBackgroundColor(getResources().getColor(R.color.white));
            screenReset = 0;
            blackDot.setVisibility(View.INVISIBLE);
            distanceText.setVisibility(View.INVISIBLE);

            misclicked = false;
            singleClicked = false;

            //determine when the next trigger will happen based on the spawnType
            // 1 = random
            // 2 = instant
            // 3 = timed
            if (spawnType == 1) {
                triggerTime = time - Math.floor(Math.random() * 1900) + 100;
            } else if (spawnType == 2) {
                triggerTime = time - 50;
            } else {
                triggerTime = time - 2000;
            }
        }

        if (time > (triggerTime + 1500) && time < (triggerTime + 2000))
            countDownText.setText("2");

        if (time > (triggerTime + 500) && time < (triggerTime + 1000))
            countDownText.setText("1");

        if (time <= triggerTime) {
            countDownText.setVisibility(View.INVISIBLE);
        }

        // next step in the updateGameState is Game Dependant
        switch (Objects.requireNonNull(gameModel.getGameType())){
            case TWITCH:
            case PRECISION:
                if (time > triggerTime && dummyTrigger == 0) {
                    countDownText.setClickable(false);
                    gameFrame.setClickable(false);
                }

                //This statement is triggered if it is time for a target to appear for the user to click
                //(time <= triggerTime) is used in case time = triggerTime
                if (time <= triggerTime) {
                    //define a random space for the target to occupy
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(targetPxSize, targetPxSize);
                    params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                    targetLeftCorner = (int) Math.floor(Math.random() * (screenWidthDp - targetPxSize));
                    targetTopCorner = (int) Math.floor(Math.random() * (screenHeightDp - targetPxSize));
                    params.leftMargin = targetLeftCorner;
                    params.topMargin = targetTopCorner;
                    singleTarget.setLayoutParams(params);

                    //now that the target is properly placed, set the target to visible
                    //and make the screen and target clickable
                    singleTargetMethod(true);

                    //to keep track of target duration without restarting this statement,
                    //set a dummy to remember when the trigger happened, then nullify the triggerTime
                    dummyTrigger = triggerTime;
                    triggerTime = 0;
                }
                else if (dummyTrigger == 0) {
                    //this trap is set such that presses can't happen if time>triggerTime
                }
                // this statement is called if it is not "trigger time" and the user has
                // successfully clicked a target
                else if (singleClicked) {
                    //update the User's score
                    attempt++;
                    success++;
                    hitTime = dummyTrigger - time;
                    scoreUpdate();

                    gameFrame.setBackgroundColor(getResources().getColor(R.color.green));

                    //reset game variables to a pre-trigger state
                    screenReset = time - 1000;
                    singleTargetMethod(false);
                    dummyTrigger = 0;
                }
                // this statement is called if it is not "trigger time" and the user has
                // successfully clicked the screen, missing the target
                else if (misclicked) {
                    //update the User's score
                    attempt++;
                    hitTime = 1000;
                    hitDistance = 250;
                    scoreUpdate();

                    gameFrame.setBackgroundColor(getResources().getColor(R.color.red));

                    //reset game variables to a pre-trigger state
                    screenReset = time - 1000;
                    singleTargetMethod(false);
                    dummyTrigger = 0;
                }
                // this statement is called if it is not "trigger time" and the user has
                // failed to press the target within targetDurationMillis
                else if (dummyTrigger - time >= 1000 && GameType.TWITCH.equals(gameModel.getGameType())) {
                    //update the User's score
                    hitDistance = 250;
                    hitTime = 1000;
                    attempt++;
                    scoreUpdate();

                    gameFrame.setBackgroundColor(getResources().getColor(R.color.red));

                    //reset game variables to a pre-trigger state
                    screenReset = time - 1000;
                    singleTargetMethod(false);
                    dummyTrigger = 0;
                    //rootView.findViewById(R.id.triggered).setVisibility(View.INVISIBLE);
                } else if (dummyTrigger - time >= targetDurationMillis && GameType.PRECISION.equals(gameModel.getGameType())) {
                    //update the User's score
                    hitDistance = 150;
                    hitTime = 1000;
                    attempt++;
                    scoreUpdate();

                    gameFrame.setBackgroundColor(getResources().getColor(R.color.red));

                    //reset game variables to a pre-trigger state
                    screenReset = time - 1000;
                    singleTargetMethod(false);
                    dummyTrigger = 0;
                    //rootView.findViewById(R.id.triggered).setVisibility(View.INVISIBLE);
                }
                break;
            case REACTION:
                //This statement is triggered if it is time for a target to appear for the user to click
                //(time <= triggerTime) is used in case time = triggerTime
                if (time <= triggerTime) {
                    DisplayMetrics displayMetrics = GameFragment.this.getActivity().
                            getResources().getDisplayMetrics();
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Math.round((screenHeightDp - 35) *
                            (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)), Math.round((screenHeightDp - 35) *
                            (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)));
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                    params.topMargin = 17;
                    singleTargetMethod(true);

                    singleTarget.setLayoutParams(params);
                    dummyTrigger = triggerTime;
                    triggerTime = 0;
                } else if (singleClicked && time < gameDurationMillis && triggerTime != 0) {
                    triggerTime = time - Math.floor(Math.random() * 1900) + 100;
                    singleClicked = false;
                    dummyTrigger = 0;
                    singleTarget.setVisibility(View.INVISIBLE);
                    attempt++;
                    hitTime = 2000;
                    scoreUpdate();
                } else if (singleClicked) {
                    triggerTime = time - Math.floor(Math.random() * 1900) + 100;
                    singleClicked = false;
                    singleTarget.setVisibility(View.INVISIBLE);
                    hitTime = dummyTrigger - time;
                    dummyTrigger = 0;
                    attempt++;
                    success++;
                    scoreUpdate();
                } else if (dummyTrigger - time >= 2000) {
                    triggerTime = time - Math.floor(Math.random() * 1900) + 100;
                    singleClicked = false;
                    singleTarget.setVisibility(View.INVISIBLE);
                    hitTime = 2000;
                    dummyTrigger = 0;
                    attempt++;
                    scoreUpdate();
                }
                break;
            case TIME_CHALLENGE:
                if (attempt > 1) {
                    double aver = Math.floor((attempt / (gameDurationMillis - time)) * 100000) / 100;
                    scoreText.setText(aver + "cps");
                }
                if (time > triggerTime) {
                    countDownText.setClickable(false);
                    gameFrame.setClickable(false);
                }
                //This statement is triggered if it is time for a target to appear for the user to click
                //(time <= triggerTime) is used in case time = triggerTime
                if (time <= triggerTime) {

                    //Set a giant target to be placed to symbolize the start of the game
                    //This target will never disappear, and the player doesn't need to actually
                    //press it
                    DisplayMetrics displayMetrics = GameFragment.this.getActivity().
                            getResources().getDisplayMetrics();
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Math.round((screenHeightDp - 100) * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)), Math.round((screenHeightDp - 100) * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)));
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                    params.topMargin = 17;
                    singleTarget.setLayoutParams(params);

                    //now that the target is properly placed, set the target to visible
                    //and make the screen and target clickable
                    singleTargetMethod(true);

                    triggerTime = -1;
                } else if (singleClicked) {
                    attempt++;
                    success++;
                    targetScoreText.setText(((int) success) + "");
                    singleClicked = false;
                }
                break;
        }
    }

    private double touchSetUp(double xPressLocation, double yPressLocation) {

        //distance calculation
        int xLocationBullseye = (int) Math.floor(targetLeftCorner + targetPxSize / 2);
        int yLocationBullseye = (int) Math.floor(targetTopCorner + targetPxSize / 2);
        double distance = Math.sqrt(Math.pow((targetLeftCorner + xLocation) - xLocationBullseye, 2)
                + Math.pow((targetTopCorner + yLocation) - yLocationBullseye, 2));

        Double truncDistance = Math.floor(distance * 100) / 100;
        hitDistance = truncDistance;

        //place black dot;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(25, 25);
        params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

        params.leftMargin = (int) (xPressLocation - 12);
        params.topMargin = (int) (yPressLocation - 12);
        blackDot.setLayoutParams(params);
        blackDot.setVisibility(View.VISIBLE);

        //set Distance Text
        RelativeLayout.LayoutParams myParams = new RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

        if (xLocationBullseye <= (screenWidthDp / 2))
            myParams.leftMargin = params.leftMargin + 25;
        else
            myParams.leftMargin = params.leftMargin - 300;

        if (yLocationBullseye >= (screenHeightDp / 2))
            myParams.topMargin = params.topMargin - 15;
        else
            myParams.topMargin = params.topMargin + 100;
        distanceText.setLayoutParams(myParams);
        distanceText.bringToFront();

        return distance;
    }

    //method used to switch between visibility settings
    //for the single target
    private void singleTargetMethod(boolean b) {
        if (b) {
            singleTarget.setVisibility(View.VISIBLE);
            singleTarget.setClickable(true);
            gameFrame.setClickable(true);
        } else {
            singleTarget.setVisibility(View.INVISIBLE);
            singleTarget.setClickable(false);
            gameFrame.setClickable(false);
        }
    }

    private void scoreUpdate() {
        DecimalFormat df;
        switch (gameModel.getGameType()){
            case PRECISION:
                df = new DecimalFormat("#.##");
                scoreTypeText.setText(R.string.accuracy);
                distanceText.setText(df.format(hitDistance) + "px");
                if (scoreActual == 0)
                    scoreActual = hitDistance;
                else
                    scoreActual = ((scoreActual * (attempt - 1)) + hitDistance) / attempt;
                scoreText.setText(df.format(scoreActual) + "px");
                break;
            case TWITCH:
            case REACTION:
                df = new DecimalFormat("#.###");
                scoreTypeText.setText(R.string.sped);
                distanceText.setText(df.format(hitTime) + "ms");
                if (scoreActual == 0)
                    scoreActual = hitTime;
                else
                    scoreActual = ((scoreActual * (attempt - 1)) + hitTime) / attempt;
                scoreText.setText(df.format(scoreActual) + "ms");
        }
        distanceText.setVisibility(View.VISIBLE);
        distanceText.bringToFront();

        targetScoreText.setText(((int) success) + "/" + ((int) attempt));
    }

    /**
     * This method is called when the game ends, either
     * by running out of time, on a back button press.
     * <p>
     * Launches the newScore method in MainActivity, passing
     * it gameType and scoreMap
     */
    private void gameOver() {
        if (muf >= gameDurationMillis)
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MenuFragment()).commit();
        else {
            quitTime = quitTime == 0 ? quitTime : Double.parseDouble(timerText.getText() + "") * 10;
            ScoreModel scoreModel = new ScoreModel(attempt, success, scoreActual, quitTime,
                    gameDurationMillis, targetPxSize, gameModel.getGameType(), gameModel.getRankAttempt());

            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new EndFragment(scoreModel)).commit();
        }
    }
}