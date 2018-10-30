package edu.uncw.seahawktours;

import android.content.Context;

public class Building {
    private String name;
    private int imageResourceId;
    private String caption;
    private String description;
    private String url;


    public static final Building[] buildings= {
        new Building ("Computer Information Systems (CIS)",R.drawable.cis, "Computer Information Systems Building Entrance", "The Computer Information Systems (CIS) building was constructed to house the Information Systems and Operations Management Department from the Cameron School of Business and the Department of Computer Science from the College of Arts and Sciences. The CIS Building, a new 51,731 square-foot state-of-the-art facility on the UNCW campus, opened January 10, 2007 for the new school semester. Work on the facility began on July 29, 2004 with the groundbreaking, and competed in December of 2006. The building cost approximately $12.8 million and was funded by the North Carolina State Bond campaign of 2000.", "https://library.uncw.edu/web/collections/archives/bnl/cis.html" ),
        new Building("Randall Library (RL)", R.drawable.randall,"Entrance to Randall Library from Chancellors Walk", "Funding to construct a library at the new campus location began in 1965 by the Friends of Wilmington College. The first on-site library was housed in Alderman Hall administration building following the move from Isaac Bear Building. In 1968, the original front of Randall Library faced College Road but since its 1980s expansion the entrance now faces Chancellors Walk. This facility contains UNCW Archives, Special Collections (Helen Hagan Room), the Gene Huguelet Conference Room, and the Honors Program offices, along with various faculty offices.", "https://library.uncw.edu/web/collections/archives/bnl/3.html"),
    };

    private Building(String name, int imageResourceId,String caption, String description, String url ){
        this.name=name;
        this.imageResourceId=imageResourceId;
        this.caption=caption;
        this.description=description;
        this.url=url;
    }

    public String getName(){return name;}

    public int getImageResourceId(){return imageResourceId;}

    public String getCaption(){return caption;}

    public String getDescription(){return description;}

    public String getUrl(){return url;}


}
