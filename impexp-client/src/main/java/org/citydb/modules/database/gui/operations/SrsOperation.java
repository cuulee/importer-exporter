/*
 * 3D City Database - The Open Source CityGML Database
 * http://www.3dcitydb.org/
 * 
 * Copyright 2013 - 2018
 * Chair of Geoinformatics
 * Technical University of Munich, Germany
 * https://www.gis.bgu.tum.de/
 * 
 * The 3D City Database is jointly developed with the following
 * cooperation partners:
 * 
 * virtualcitySYSTEMS GmbH, Berlin <http://www.virtualcitysystems.de/>
 * M.O.S.S. Computer Grafik Systeme GmbH, Taufkirchen <http://www.moss.de/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.citydb.modules.database.gui.operations;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.citydb.config.Config;
import org.citydb.config.i18n.Language;
import org.citydb.config.project.database.DBOperationType;
import org.citydb.config.project.database.DatabaseSrs;
import org.citydb.config.project.database.Workspace;
import org.citydb.config.project.global.LogLevel;
import org.citydb.database.connection.DatabaseConnectionPool;
import org.citydb.gui.components.dialog.StatusDialog;
import org.citydb.gui.factory.PopupMenuDecorator;
import org.citydb.gui.factory.SrsComboBoxFactory;
import org.citydb.gui.factory.SrsComboBoxFactory.SrsComboBox;
import org.citydb.gui.util.GuiUtil;
import org.citydb.log.Logger;
import org.citydb.plugin.extension.view.ViewController;

public class SrsOperation extends DatabaseOperationView {
	private final ReentrantLock mainLock = new ReentrantLock();
	private final Logger LOG = Logger.getInstance();
	private final DatabaseOperationsPanel parent;
	private final ViewController viewController;
	private final DatabaseConnectionPool dbConnectionPool;
	private final Config config;

	private JPanel component;
	private JLabel srsComboBoxLabel;
	private SrsComboBoxFactory srsComboBoxFactory;
	private SrsComboBox srsComboBox;
	private ActionListener srsComboBoxListener;
	private JLabel sridLabel;
	private JFormattedTextField sridText;
	private JLabel srsNameLabel;
	private JTextField gmlSrsNameText;
	private JLabel dbContent;
	private ButtonGroup transformButtonGroup;
	private JRadioButton transform;
	private JRadioButton updateMetadataOnly;
	private JButton checkButton;
	private JButton changeSrsButton;

	public SrsOperation(DatabaseOperationsPanel parent, Config config) {
		this.parent = parent;
		this.config = config;
		
		viewController = parent.getViewController();
		dbConnectionPool = DatabaseConnectionPool.getInstance();
		
		init();
	}

	private void init() {
		component = new JPanel();
		component.setLayout(new GridBagLayout());

		srsComboBoxLabel = new JLabel();
		srsComboBoxFactory = SrsComboBoxFactory.getInstance(config);
		srsComboBox = srsComboBoxFactory.createSrsComboBox(false);
		
		sridLabel = new JLabel();
		DecimalFormat tileFormat = new DecimalFormat("##########");	
		tileFormat.setMaximumIntegerDigits(10);
		tileFormat.setMinimumIntegerDigits(1);
		sridText = new JFormattedTextField(tileFormat);
		checkButton = new JButton();
		srsNameLabel = new JLabel();
		gmlSrsNameText = new JTextField();
		
		dbContent = new JLabel();
		transformButtonGroup = new ButtonGroup();
		transform = new JRadioButton();
		updateMetadataOnly = new JRadioButton();

		changeSrsButton = new JButton();

		PopupMenuDecorator.getInstance().decorate(sridText, gmlSrsNameText);

		sridText.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (sridText.getValue() != null) {
					if (((Number)sridText.getValue()).intValue() < 0)
						sridText.setValue(0);
					else if (((Number)sridText.getValue()).intValue() > Integer.MAX_VALUE)
						sridText.setValue(Integer.MAX_VALUE);
				}
			}
		});
		
		JPanel srsBox = new JPanel();
		srsBox.setLayout(new GridBagLayout());
		GridBagConstraints c = GuiUtil.setConstraints(0,0,1.0,0.0,GridBagConstraints.BOTH,10,5,0,5);
		c.gridwidth = 2;
		srsBox.add(srsComboBoxLabel, GuiUtil.setConstraints(0,0,0.0,0.0,GridBagConstraints.HORIZONTAL,5,5,0,5));
		srsBox.add(srsComboBox, GuiUtil.setConstraints(1,0,2,1,1.0,0.0,GridBagConstraints.BOTH,5,5,0,5));
		
		srsBox.add(sridLabel, GuiUtil.setConstraints(0,1,0,0,GridBagConstraints.BOTH,5,5,0,5));
		srsBox.add(sridText, GuiUtil.setConstraints(1,1,2,1,1,0,GridBagConstraints.HORIZONTAL,5,5,0,5));
		
		srsBox.add(srsNameLabel, GuiUtil.setConstraints(0,2,0,0,GridBagConstraints.BOTH,5,5,0,5));
		srsBox.add(gmlSrsNameText, GuiUtil.setConstraints(1,2,2,1,1,0,GridBagConstraints.HORIZONTAL,5,5,0,5));

		srsBox.add(dbContent, GuiUtil.setConstraints(0,3,0,0,GridBagConstraints.BOTH,5,5,0,5));
		transformButtonGroup.add(transform);
		transform.setIconTextGap(10);
		transformButtonGroup.add(updateMetadataOnly);
		updateMetadataOnly.setIconTextGap(10);
		updateMetadataOnly.setSelected(true);
		srsBox.add(transform, GuiUtil.setConstraints(1,3,0.0,1.0,GridBagConstraints.HORIZONTAL,5,5,0,5));
		srsBox.add(updateMetadataOnly, GuiUtil.setConstraints(2,3,0.0,1.0,GridBagConstraints.HORIZONTAL,5,5,0,5));

		component.add(srsBox, c);
		
		Box buttonsPanel = Box.createHorizontalBox();
		buttonsPanel.add(checkButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonsPanel.add(changeSrsButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		component.add(buttonsPanel, GuiUtil.setConstraints(0,1,3,1,1,0,GridBagConstraints.NONE,10,5,5,5));

		// influence focus policy
		checkButton.setFocusCycleRoot(false);

		srsComboBoxListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displaySelectedValues();
			}
		};
		
		srsComboBox.addActionListener(srsComboBoxListener);
		
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkSrs();
			}
		});

		changeSrsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				Thread thread = new Thread() {
					public void run() {
						changeSrs();
					}
				};
				thread.setDaemon(true);
				thread.start();
			}
		});
	}

	@Override
	public String getLocalizedTitle() {
		return Language.I18N.getString("db.label.operation.srs");
	}

	@Override
	public Component getViewComponent() {
		return component;
	}

	@Override
	public String getToolTip() {
		return null;
	}

	@Override
	public Icon getIcon() {
		return null;
	}

	@Override
	public DBOperationType getType() {
		return DBOperationType.SRS;
	}

	@Override
	public void doTranslation() {
		srsComboBoxLabel.setText(Language.I18N.getString("common.label.boundingBox.crs"));
		sridLabel.setText(Language.I18N.getString("pref.db.srs.label.srid"));
		srsNameLabel.setText(Language.I18N.getString("pref.db.srs.label.srsName"));
		dbContent.setText(Language.I18N.getString("db.label.operation.srs.dbContent"));
		transform.setText(Language.I18N.getString("db.label.operation.srs.transform"));
		updateMetadataOnly.setText(Language.I18N.getString("db.label.operation.srs.updateMetadataOnly"));
		checkButton.setText(Language.I18N.getString("pref.db.srs.button.check"));
		changeSrsButton.setText(Language.I18N.getString("db.button.srs.changesrs"));
	}

	@Override
	public void setEnabled(boolean enable) {
		srsComboBoxLabel.setEnabled(enable);
		srsComboBox.setEnabled(enable);
		sridLabel.setEnabled(enable);
		srsNameLabel.setEnabled(enable);
		dbContent.setEnabled(enable);
		transform.setEnabled(enable);
		updateMetadataOnly.setEnabled(enable);
		checkButton.setEnabled(enable);
		changeSrsButton.setEnabled(enable);
	}

	@Override
	public void loadSettings() {
		displaySelectedValues();
	}

	@Override
	public void setSettings() {
		config.getProject().getDatabase().addDefaultReferenceSystems();
		srsComboBoxFactory.updateAll(true);
	}

	private void displaySelectedValues() {
		DatabaseSrs refSys = srsComboBox.getSelectedItem();
		if (refSys == null) 
			return;

		sridText.setValue(refSys.getSrid());
		gmlSrsNameText.setText(refSys.getGMLSrsName());
		srsComboBox.setToolTipText(refSys.getDescription());
		
		boolean isDBSrs = !srsComboBox.isDBReferenceSystemSelected();
		sridText.setEditable(isDBSrs);
		gmlSrsNameText.setEditable(isDBSrs);
	}
	
	private int checkSrs() {
		int srid =  0;
		try {
			srid = Integer.parseInt(sridText.getText().trim());
		} catch (NumberFormatException nfe) {
			//
		}

		try {
			DatabaseSrs tmp = DatabaseSrs.createDefaultSrs();
			tmp.setSrid(srid);
			dbConnectionPool.getActiveDatabaseAdapter().getUtil().getSrsInfo(tmp);
			if (tmp.isSupported()) {
				LOG.all(LogLevel.INFO, "SRID " + srid + " is supported.");
				LOG.all(LogLevel.INFO, "Database name: " + tmp.getDatabaseSrsName());
				LOG.all(LogLevel.INFO, "SRS type: " + tmp.getType());
			} else
				LOG.all(LogLevel.WARN, "SRID " + srid + " is NOT supported.");
			
		} catch (SQLException sqlEx) {
			LOG.error("Error while checking user-defined SRSs: " + sqlEx.getMessage().trim());
		}
		
		return srid;
	}
	
	private void changeSrs() {
		final ReentrantLock lock = this.mainLock;
		lock.lock();

		try {
			Workspace workspace = parent.getWorkspace();
			if (workspace == null)
				return;

			viewController.clearConsole();
			viewController.setStatusText(Language.I18N.getString("main.status.database.srs.label"));

			if (dbConnectionPool.getActiveDatabaseAdapter().hasVersioningSupport() && !parent.existsWorkspace())
				return;

			final StatusDialog dialog = new StatusDialog(viewController.getTopFrame(), 
					Language.I18N.getString("db.dialog.srs.window"), 
					Language.I18N.getString("db.dialog.srs.title"), 
					null,
					Language.I18N.getString("db.dialog.srs.details"), 
					true);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					dialog.setLocationRelativeTo(viewController.getTopFrame());
					dialog.setVisible(true);
				}
			});

			dialog.getButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dbConnectionPool.getActiveDatabaseAdapter().getUtil().interruptDatabaseOperation();
						}
					});
				}
			});

			try {
				String schema = dbConnectionPool.getActiveDatabaseAdapter().getConnectionDetails().getSchema();
				int srid = checkSrs();
				DatabaseSrs targetSrs = new DatabaseSrs(srid);
				targetSrs.setGMLSrsName(gmlSrsNameText.getText());
				
				LOG.all(LogLevel.INFO, "Changing reference system...");
				dbConnectionPool.getActiveDatabaseAdapter().getUtil().changeSrs(targetSrs, transform.isSelected(), schema);
			
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						dialog.dispose();
					}
				});

				LOG.all(LogLevel.INFO, "Changing reference system successfully finished.");
			} catch (SQLException sqlEx) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						dialog.dispose();
					}
				});

				String sqlExMsg = sqlEx.getMessage().trim();
				String text = Language.I18N.getString("db.dialog.srs.error");
				Object[] args = new Object[]{ sqlExMsg };
				String result = MessageFormat.format(text, args);

				JOptionPane.showMessageDialog(
						viewController.getTopFrame(), 
						result, 
						Language.I18N.getString("common.dialog.errordb.title"),
						JOptionPane.ERROR_MESSAGE);

				LOG.error("SQL error: " + sqlExMsg);
			} finally {
				viewController.setStatusText(Language.I18N.getString("main.status.ready.label"));
			}

		} finally {
			lock.unlock();
		}
	}

}
