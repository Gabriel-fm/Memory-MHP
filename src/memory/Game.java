/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  This file is part of MEMORY-MHP											 * 
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

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.tv.xlet.*;
import org.dvb.ui.DVBTextLayoutManager;
import org.havi.ui.HComponent;
import org.havi.ui.HContainer;
import org.havi.ui.HGraphicButton;
import org.havi.ui.HIcon;
import org.havi.ui.HScene;
import org.havi.ui.HText;
import org.havi.ui.event.HActionListener;

public class Game extends Thread implements Xlet, HActionListener {

	XletContext contexto;
	GestorPantalla gp = new GestorPantalla();
	static HScene escena = null;

	// Elemento que manipula las cartas
	Cartas cards = new Cartas();

	// Elemento que manipula los niveles
	Niveles levels;

	// Area en la que se dibujaran las cartas
	public HContainer superficie;

	focoBotones foco = new focoBotones();
	HComponent[] hc;

	static HText infoNiv;
	static HText infoTim;

	static int nivel = 2;
	static int tiempo = 2;

	Contador cont =  new Contador(this);
	private Teclas teclasColores;
	private HIcon fondo = null;

	public void initXlet(XletContext ctx) throws XletStateChangeException {

		contexto = ctx;
		Video.reducir(20, 20, 0, 0, contexto);

		escena = gp.HScenePantallaCompleta();

		teclasColores = new Teclas(this);

		// TODO Fondo pantalla
	}

	public void startXlet() throws XletStateChangeException {
		start();

	}

	public void pauseXlet() {

	}

	public void destroyXlet(boolean bool) throws XletStateChangeException {
		escena.removeAll();
		escena.dispose();
		escena = null;
		teclasColores.repositoryExclusivo.removeAllColourKeys();
		
		teclasColores.repositoryExclusivo = null;
		//teclasColores.eventManager.removeAllUserEventListeners();

		teclasColores.eventManager.removeUserEventListener(teclasColores);
		teclasColores.eventManager = null;
		teclasColores = null;

		Video.cerrar();

		contexto.notifyDestroyed();

	}

	public void run() {

		escena.setVisible(true);

		configuracion();

		// superficie = new HContainer(20,20,500,500);
		// escena.add(superficie);
		// superficie.setVisible(true);
		//		
		// levels.getNuevoNivel();

	}

	public void configuracion() {
		if (fondo == null) {
			fondo = new org.havi.ui.HIcon(java.awt.Toolkit.getDefaultToolkit()
					.getImage("Images/interfaz.gif"));
			fondo.setBounds(0, 0, 720, 576);
			fondo.setVisible(true);
		}

		escena.removeAll();
		escena.repaint();
		escena.add(fondo);
		levels = new Niveles(this);
		hc = new org.havi.ui.HComponent[5];
		
		Font fgpl = new Font("Tiresias", 9, Font.PLAIN);
		HText gpl = new HText("GNU GPLv2");
		gpl.setForeground(java.awt.Color.gray);
		gpl.setFont(fgpl);
		gpl.setSize(120, 50);
		gpl.setLocation(35, 490);
		gpl.setVisible(true);
		escena.add(gpl);

		DVBTextLayoutManager dvbm = new DVBTextLayoutManager();
		dvbm.setHorizontalAlign(DVBTextLayoutManager.HORIZONTAL_START_ALIGN);
		// Font fuente = new Font("Tiresias", Font.BOLD, 32);
		// Font fuente2 = new Font("Tiresias", Font.BOLD, 28);

		Image imgNivel = java.awt.Toolkit.getDefaultToolkit().getImage(
				"Images/bt_nivel.gif");
		while (imgNivel.getWidth(null) < 2)
			;
		while(imgNivel.getHeight(null) < 2);
		HGraphicButton bNivel = new HGraphicButton(imgNivel);
		bNivel.setSize(imgNivel.getWidth(null), imgNivel.getHeight(null));
		bNivel.addHActionListener(foco);
		bNivel.setActionCommand("nivel");
		hc[0] = bNivel;
		Image imgTiempo = java.awt.Toolkit.getDefaultToolkit().getImage(
				"Images/bt_tiempo.gif");
		while (imgTiempo.getWidth(null) < 2)
			;
		while (imgTiempo.getHeight(null) < 2);
		HGraphicButton bTiempo = new HGraphicButton(imgTiempo);
		bTiempo.setSize(imgTiempo.getWidth(null), imgTiempo.getHeight(null));
		bTiempo.addHActionListener(foco);
		bTiempo.setActionCommand("tiempo");
		hc[1] = bTiempo;
		Image imgJugar = java.awt.Toolkit.getDefaultToolkit().getImage(
				"Images/bt_iniciar.gif");
		while (imgJugar.getWidth(null) < 2)
			;
		while (imgJugar.getHeight(null) < 2);
		HGraphicButton bJugar = new HGraphicButton(imgJugar);
		bJugar.setSize(imgJugar.getWidth(null), imgJugar.getHeight(null));
		bJugar.addHActionListener(this);
		hc[2] = bJugar;
		int separacion = 50;
		for (int i = 0; i < hc.length - 2; i++) {
			hc[i].setLocation(130, 210 + (separacion * i));
			escena.add(hc[i]);
			hc[i].setVisible(true);
			escena.popToFront(hc[i]);
		}
		
		bNivel.setFocusTraversal(null, bTiempo, null, null);
		bTiempo.setFocusTraversal(bNivel, bJugar, null, null);
		bJugar.setFocusTraversal(bTiempo, null, null, null);
		bNivel.requestFocus();
		infoNiv = new HText("Nivel: Normal");
		infoNiv.setBounds(bNivel.getX()+25+bNivel.getWidth(), bNivel.getY(), 180, 120);
		infoNiv.setTextLayoutManager(dvbm);
		infoTim = new HText("Tiempo: 2 min");
		infoTim.setBounds(bTiempo.getX()+25+bTiempo.getWidth(), bTiempo.getY(), 180, 120);
		infoTim.setTextLayoutManager(dvbm);
		 escena.add(infoNiv);
		 escena.add(infoTim);
		 escena.popToFront(infoNiv);
		 escena.popToFront(infoTim);
		 hc[3] = infoNiv;
		 hc[4] = infoTim;

		Font fuenteC = new Font("Tiresias", Font.PLAIN, 38);
		cont = null;
		cont = new Contador(this);
		cont.ht.setFont(fuenteC);
		cont.ht.setBounds(550, 480, 100, 80);
		cont.ht.setForeground(java.awt.Color.black);
		escena.add(cont.ht);
		cont.ht.setVisible(true);

		escena.pushToBack(fondo);

		cont.setTiempo(0);
		Image im1 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"Images/bt_reiniciar.gif");
		while (im1.getWidth(null) < 3)
			;
		while (im1.getHeight(null) < 3)
			;
		HIcon icon1 = new HIcon(im1);
		icon1.setBounds(525, 175, im1.getWidth(null), im1.getHeight(null));
		icon1.setVisible(true);
		escena.add(icon1);
		escena.popToFront(icon1);
		Image im2 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"Images/bt_terminar.gif");
		while (im2.getWidth(null) < 2)
			;
		while (im2.getHeight(null) < 2)
			;
		HIcon icon2 = new HIcon(im2);
		icon2.setBounds(525, 210, im2.getWidth(null), im2.getHeight(null));
		icon2.setVisible(true);
		escena.add(icon2);
		escena.popToFront(icon2);
		Image im3 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"Images/bt_salir.gif");
		while (im3.getWidth(null) < 3)
			;
		while (im3.getHeight(null) < 3)
			;
		HIcon icon3 = new HIcon(im3);
		icon3.setBounds(525, 245, im3.getWidth(null), im3.getHeight(null));
		icon3.setVisible(true);
		escena.add(icon3);
		escena.popToFront(icon3);
		
//		repositorio();
	}

//	private void repositorio() {
//		eventManager=EventManager.getInstance();
//		UserEventRepository repo = new UserEventRepository("repo");
//		repo.addAllArrowKeys();
//		
//		eventManager.addUserEventListener(this,repo);
//		
//	}

	public void jugar(int tiempo, int nivel) {

		levels.getNuevoNivel(tiempo, nivel);
	}

	public void actionPerformed(ActionEvent ev) {

		if (nivel != -1 && tiempo != 0)
			jugar(tiempo, nivel);
	}

	public class focoBotones implements HActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("tiempo")) {
				if (Game.tiempo == 0) {
					Game.tiempo = 1;
					Game.infoTim.setTextContent("Tiempo: 1 min",
							HText.ALL_STATES);
				} else if (Game.tiempo == 1) {
					Game.tiempo = 2;
					Game.infoTim.setTextContent("Tiempo: 2 min",
							HText.ALL_STATES);
				} else if (Game.tiempo == 2) {
					Game.tiempo = 3;
					Game.infoTim.setTextContent("Tiempo: 3 min",
							HText.ALL_STATES);
				} else if (Game.tiempo == 3) {
					Game.tiempo = 1;
					Game.infoTim.setTextContent("Tiempo: 1 min",
							HText.ALL_STATES);
				}
			}

			if (e.getActionCommand().equals("nivel")) {
				if (Game.nivel == -1) {
					Game.nivel = 1;
					Game.infoNiv.setTextContent("Nivel: Facil",
							HText.ALL_STATES);
				} else if (Game.nivel == 1) {
					Game.nivel = 2;
					Game.infoNiv.setTextContent("Nivel: Normal",
							HText.ALL_STATES);
				} else if (Game.nivel == 2) {
					Game.nivel = 3;
					Game.infoNiv.setTextContent("Nivel: Dificil",
							HText.ALL_STATES);
				} else if (Game.nivel == 3) {
					Game.nivel = 1;
					Game.infoNiv.setTextContent("Nivel: Facil",
							HText.ALL_STATES);
				}

			}

		}

	}

//	public void userEventReceived(UserEvent e) {
//		if (e.getCode() == HKeyEvent.VK_COLORED_KEY_2) {
//			try {
//				this.destroyXlet(true);
//			} catch (XletStateChangeException e1) {
//				e1.printStackTrace();
//			}
//		} else if (e.getCode() == HKeyEvent.VK_COLORED_KEY_0) {
//			this.configuracion();
//		} else if (e.getCode() == HKeyEvent.VK_COLORED_KEY_1) {
//			this.configuracion();
//			jugar(nivel,tiempo);
//		}
//		
//	}
}
