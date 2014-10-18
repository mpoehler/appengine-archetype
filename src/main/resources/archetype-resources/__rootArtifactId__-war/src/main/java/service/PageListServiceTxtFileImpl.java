#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.service;

import ${package}.entity.PageListEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 13.10.14.
 */
public class PageListServiceTxtFileImpl implements PageListService {

    public static Log logger = LogFactory.getLog(PageListServiceTxtFileImpl.class);

    private Resource txtFile;

    @Override
    public List<PageListEntry> getPages() {
        List<PageListEntry> list = new ArrayList<PageListEntry>();
        try {
            LineNumberReader lnr = new LineNumberReader(new InputStreamReader(txtFile.getInputStream()));
            String line = null;
            while ((line = lnr.readLine()) != null) {
                PageListEntry ple = null;
                try {
                    ple = PageListEntry.parse(line);
                } catch (ParseException e) {
                    logger.error("Parse error when reading Pages list file from: " + txtFile.getFilename() + ", Error is in line: " + line);
                }
                if (ple != null) {
                    list.add(ple);
                }
            }
        } catch (IOException ioe) {
            logger.error("Can't read txt file from " + txtFile.getFilename());
        }

        return list;
    }

    public Resource getTxtFile() {
        return txtFile;
    }

    public void setTxtFile(Resource txtFile) {
        this.txtFile = txtFile;
    }
}
