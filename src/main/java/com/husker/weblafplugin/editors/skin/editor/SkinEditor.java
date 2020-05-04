package com.husker.weblafplugin.editors.skin.editor;

import com.alee.managers.style.XmlSkin;

import com.husker.weblafplugin.components.IconViewer;
import com.husker.weblafplugin.components.parameter.Parameter;
import com.husker.weblafplugin.components.parameter.ParameterBlock;
import com.husker.weblafplugin.components.parameter.ParameterManager;
import com.husker.weblafplugin.editors.skin.editor.variables.Icon;
import com.husker.weblafplugin.parameters.*;
import com.husker.weblafplugin.editors.skin.editor.variables.*;

import com.husker.weblafplugin.editors.skin.editor.variables.Class;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.*;

public class SkinEditor extends SkinEditorUI {

    public static final int IMAGE_SIZE = 90;
    public static final int IMAGE_PARAMETER_SIZE = Parameter.DEFAULT_WIDTH - IMAGE_SIZE - 35;

    protected IconViewer p_large_icon = new IconViewer(IMAGE_SIZE){{
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    }};
    protected TextParameter p_title = new TextParameter("Title", IMAGE_PARAMETER_SIZE);
    protected TextParameter p_author = new TextParameter("Author", IMAGE_PARAMETER_SIZE);
    protected ImageChooserParameter p_icon = new ImageChooserParameter("Icon", IMAGE_PARAMETER_SIZE);
    protected TextParameter p_description = new TextParameter("Description");

    protected ClassChooserParameter p_class = new ClassChooserParameter("Class", XmlSkin.class);
    protected TextButtonParameter p_id = new TextButtonParameter("Id", "Auto");
    protected SupportedSystemsParameter p_supported_systems = new SupportedSystemsParameter("OS support");

    protected LabelParameter p_resources_path = new LabelParameter("Path");
    protected ResourceListParameter p_include = new ResourceListParameter("Include", XmlFileType.INSTANCE);

    public SkinEditor(Project project, VirtualFile file) {
        super(project, file);

        // Component creation
        add(new ParameterBlock("Information"){{
            add(new JPanel(){{
                setLayout(new BorderLayout());
                setBorder(BorderFactory.createEmptyBorder(0, 0, -5, 0));

                add(new JPanel(){{
                    setLayout(new VerticalFlowLayout(0, 5));
                    add(p_title);
                    add(p_author);
                    add(p_icon);
                }}, BorderLayout.WEST);
                add(p_large_icon);
            }});


            add(p_description);
        }});

        add(new ParameterBlock("Settings"){{
            add(p_class);
            add(p_id);
            add(p_supported_systems);
        }});

        add(new ParameterBlock("Resources"){{
            add(p_resources_path);
            add(p_include);
        }});

        // Binding
        ParameterManager.register(p_large_icon, new Icon(SkinEditor.this));
        ParameterManager.register(p_title, new Title(SkinEditor.this));
        ParameterManager.register(p_author, new Author(SkinEditor.this));
        ParameterManager.register(p_icon, new Icon(SkinEditor.this));
        ParameterManager.register(p_description, new Description(SkinEditor.this));

        ParameterManager.register(p_class, new Class(SkinEditor.this));
        ParameterManager.register(p_id, new Id(SkinEditor.this));
        ParameterManager.register(p_supported_systems, new SupportedSystem(SkinEditor.this));

        ParameterManager.register(p_resources_path, new ResourcePath(SkinEditor.this));
        ParameterManager.register(p_include, new Include(SkinEditor.this));

        p_id.addButtonListener(e -> p_id.setText(generateId()));

        ParameterManager.reloadVariables(this);
    }

    public String generateId(){
        String id = "";
        if(!p_author.getText().replaceAll("\\s", "").equals(""))
            id = p_author.getText().toLowerCase().split(" ")[0] + ".";
        else
            id = "";
        id += p_title.getText().toLowerCase().replaceAll("\\s", ".");
        return id;
    }
}
