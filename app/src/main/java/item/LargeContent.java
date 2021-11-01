package item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LargeContent
{
    @Expose
    @SerializedName("id") private String id;

    @Expose
    @SerializedName("type") private String type;

    @Expose
    @SerializedName("content") private String content;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
