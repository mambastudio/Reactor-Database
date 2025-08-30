/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.dialog;

import com.mamba.mambaui.control.Tile;
import com.mamba.mambaui.modal.ModalDialog;
import com.nupea.reactordatabase.data.Reactor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

/**
 *
 * @author user
 */
public class ReactorDialog extends ModalDialog<Reactor>{
    private final ReactorDialogPane pane;
    public ReactorDialog() {
        var header = new Tile("Reactor Settings");
        var dialogPane = new ReactorDialogPane();
        super((handle, dialog) -> {            
            var cancel = new Button("Cancel");
            cancel.setOnAction(e -> handle.cancel());
            
            var buttonBar = new ButtonBar();
            ButtonBar.setButtonData(cancel, ButtonBar.ButtonData.CANCEL_CLOSE);
            buttonBar.getButtons().addAll(cancel);
            
            dialog.setDialogSize(900, 600);
            handle.setHeader(header);
            handle.setContent(dialogPane);
            handle.setFooter(buttonBar);          
        });
        
        this.pane = dialogPane;
    }
    
    public void setReactor(Reactor reactor){
        pane.setReactor(reactor);
    }
}
