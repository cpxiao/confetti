/**
 * Copyright (C) 2016 Robinhood Markets, Inc.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jinatonic.confetti.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractActivity extends AppCompatActivity implements View.OnClickListener {
    protected ViewGroup container;

    protected int goldDark, goldMed, gold, goldLight;
    protected int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confetti);

        container = (ViewGroup) findViewById(R.id.container);
        findViewById(R.id.generate_confetti_once_btn).setOnClickListener(this);
        findViewById(R.id.generate_confetti_stream_btn).setOnClickListener(this);
        findViewById(R.id.generate_confetti_infinite_btn).setOnClickListener(this);

//        final Resources res = getResources();
//        goldDark = res.getColor(R.color.gold_dark);
//        goldMed = res.getColor(R.color.gold_med);
//        gold = res.getColor(R.color.gold);
//        goldLight = res.getColor(R.color.gold_light);

        Context context = getApplicationContext();
        goldDark = ContextCompat.getColor(context, R.color.gold_dark);
        goldMed = ContextCompat.getColor(context, R.color.gold_med);
        gold = ContextCompat.getColor(context, R.color.gold);
        goldLight = ContextCompat.getColor(context, R.color.gold_light);
        colors = new int[]{goldDark, goldMed, gold, goldLight};
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if (id == R.id.generate_confetti_once_btn) {
            generateOnce();
        } else if (id == R.id.generate_confetti_stream_btn) {
            generateStream();
        } else {
            generateInfinite();
        }
    }

    protected abstract void generateOnce();

    protected abstract void generateStream();

    protected abstract void generateInfinite();
}
