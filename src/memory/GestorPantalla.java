/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  MEMORY-MHP - The game of Memory for MHP platforms						 *
 *  Copyright (C) 2008  Gabriel Franco (find.the.error@gmail.com)				 *
 *																		 	 *
 *  This program is free software; you can redistribute it and/or modify	 *
 *  it under the terms of the GNU General Public License as published by	 *
 *  the Free Software Foundation; either version 2 of the License, or		 *
 *  (at your option) any later version. See LICENSE document for details. 	 *
 *																	     	 *
 *  This program is distributed in the hope that it will be useful, 		 *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 		     *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   		 *
 *  GNU General Public License for more details.							 *
 *																		     *
 *  You should have received a copy of the GNU General Public License along  *
 *  with this program; if not, write to the Free Software Foundation, Inc.,  *
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.			     *
 *             															     *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package memory;

import java.io.IOException;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.havi.ui.HBackgroundConfigTemplate;
import org.havi.ui.HBackgroundDevice;
import org.havi.ui.HBackgroundImage;
import org.havi.ui.HConfigurationException;
import org.havi.ui.HGraphicsDevice;
import org.havi.ui.HPermissionDeniedException;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HScreen;
import org.havi.ui.HStillImageBackgroundConfiguration;
import org.havi.ui.HVideoDevice;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;

public class GestorPantalla implements ResourceClient {

	private HScreen pantalla;

	private HGraphicsDevice planoGrafico;
	private HVideoDevice planoVideo;
	private HBackgroundDevice planoBackground;

	private HScene escena;

	public GestorPantalla() {
		pantalla = HScreen.getDefaultHScreen();

		planoGrafico = pantalla.getDefaultHGraphicsDevice();
		planoVideo = pantalla.getDefaultHVideoDevice();
		planoBackground = pantalla.getDefaultHBackgroundDevice();
	}

	public HScene HScenePantallaCompleta() {

		HSceneFactory hsceneFactory = HSceneFactory.getInstance();
		escena = hsceneFactory.getFullScreenScene(planoGrafico);

		System.out.println("Creada Hscene: " + escena.toString());
		return (escena);
	}

	public void fondoPantalla(String imagen) {
		//org.havi.ui.HBackgroundConfiguration Config;
        
        HBackgroundConfigTemplate plantilla = new HBackgroundConfigTemplate();
        plantilla.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, HBackgroundConfigTemplate.REQUIRED);
        
        final org.havi.ui.HBackgroundConfiguration Config = planoBackground.getBestConfiguration(plantilla);       
        
        if (planoBackground.reserveDevice(this)) {
            try {
                planoBackground.setBackgroundConfiguration(Config);
                
                if (Config instanceof HStillImageBackgroundConfiguration) {
                    final HBackgroundImage backimage = new HBackgroundImage(imagen);
                    backimage.load(new HBackgroundImageListener() {
                        public void imageLoaded(HBackgroundImageEvent e) {
                            if (e.getID() == HBackgroundImageEvent.BACKGROUNDIMAGE_LOADED) {
                                try {
                                    ((HStillImageBackgroundConfiguration) Config).displayImage(backimage);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                } catch (HPermissionDeniedException ex) {
                                    ex.printStackTrace();
                                } catch (HConfigurationException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        
                        public void imageLoadFailed(HBackgroundImageEvent e) {
                            
                        }
                    });
                    
                   // ((HStillImageBackgroundConfiguration) Config).displayImage(backimage);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
	}

	public void notifyRelease(ResourceProxy proxy) {
		planoBackground.releaseDevice();
        planoVideo.releaseDevice();
        planoGrafico.releaseDevice();
	}

	public void release(ResourceProxy proxy) {
		planoBackground.releaseDevice();
        planoVideo.releaseDevice();
        planoGrafico.releaseDevice();
	}

	public boolean requestRelease(ResourceProxy proxy, Object requestData) {

		return false;
	}

	
}
