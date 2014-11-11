package ru.ifmo.md.colloquium2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private ArrayList<CandidateData> candidates = new ArrayList<CandidateData>();
    private CandidateAdapter candidateAdapter;
    private boolean began = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.post_list);
        candidateAdapter = new CandidateAdapter(this, R.layout.candidate_item, candidates);
        listView.setAdapter(candidateAdapter);
        listView.setOnItemClickListener(contentShower);
    }

    private AdapterView.OnItemClickListener contentShower = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (began) {
                CandidateData data = candidates.get(position);
                data.setNumOfVotes(data.getNumOfVotes() + 1);
                updateData();
                candidateAdapter.notifyDataSetChanged();
            }
        }
    };

    private void updateData() {
        int sum = 0;
        for (CandidateData d : candidates) {
            sum += d.getNumOfVotes();
        }
        if (sum != 0) {
            for (CandidateData d : candidates) {
                d.setPercents(d.getNumOfVotes() * 10000 / sum);
            }
        }
    }

    public void addCandidate(View view) {
        if (!began) {
            CandidateData data = new CandidateData();
            EditText editText = (EditText) findViewById(R.id.edit_candidate_name);
            data.setCandidateName(editText.getText().toString());
            data.setNumOfVotes(0);
            data.setPercents(0);
            candidates.add(data);
            candidateAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_begin_elections) {
            began = true;
            return true;
        }

        if (id == R.id.action_end_elections) {
            began = false;
            return true;
        }

        if (id == R.id.action_clear_results) {
            for (CandidateData d : candidates) {
                d.setNumOfVotes(0);
                d.setPercents(0);
            }
            candidateAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
