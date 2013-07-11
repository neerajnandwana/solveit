package com.solveit.data.store;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.inject.Singleton;
import com.solveit.data.Winner;
import com.solveit.util.Const;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml3;

@Singleton
public class FileStore implements Store, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(FileStore.class);

	@Override
	public void saveWinner(String winnerName, String ip) {
		String data = Winner.Builder.build(escapeHtml(getTimestamp()), escapeHtml(ip), escapeHtml(winnerName)).toCsv();
		try {
			Files.append(data, getStoreFile(), Charsets.US_ASCII);
		} catch (Exception e) {
			LOG.error("error file writing in the file\n" + e);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<Winner> getWinners() {
		List<Winner> winners = Lists.newArrayList();
		try {
			for (String csv: Resources.readLines(getStoreFile().toURL(), Charsets.US_ASCII)){
                Winner winner = Winner.Builder.csvBuild(csv);
                if(Const.SHOW_UNIQUE_WINNERS ){
                    if(!winners.contains(winner)){
                        winners.add(winner);
                    }
                } else {
                    winners.add(winner);
                }
            }
		} catch (IOException e) {
			LOG.error("error file reading the winners from the file\n" + e);
		}
        return 	winners;
	}

	private File getStoreFile() throws IOException {
		File file = new File(Const.DATA_FILE);
		file.createNewFile(); // creates new file if its not there
		return file;
	}

	private String getTimestamp(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp.toString();
	}

	private String escapeHtml(String in){
		return escapeHtml3(in);
	}
}
