/*
 * http://code.google.com/p/ametro/
 * Transport map viewer for Android platform
 * Copyright (C) 2009-2010 Roman.Golovanov@gmail.com and other
 * respective project committers (see project home page)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.ametro.util;

import android.util.Log;
import org.ametro.MapSettings;
import org.ametro.model.City;
import org.ametro.model.SubwayMap;
import org.ametro.model.SubwayMapBuilder;
import org.ametro.pmz.FilePackage;
import org.ametro.pmz.GenericResource;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.ametro.Constants.LOG_TAG_MAIN;

/**
 * @author Vlad Vinichenko (akerigan@gmail.com)
 *         Date: 12.02.2010
 *         Time: 15:03:07
 */
public class ModelUtil {

    public static City indexPmz(String fileName) throws IOException {
        Date startTimestamp = new Date();
        City model = new City();
        FilePackage pkg = new FilePackage(fileName);
        GenericResource info = pkg.getCityGenericResource();
        String countryName = info.getValue("Options", "Country");
        String cityName = info.getValue("Options", "RusName");
        if (cityName == null) {
            cityName = info.getValue("Options", "CityName");
        }
        model.countryName = countryName;
        model.cityName = cityName;
        model.sourceVersion = MapSettings.getSourceVersion();
        File pmzFile = new File(fileName);
        model.timestamp = pmzFile.lastModified();
        if (Log.isLoggable(LOG_TAG_MAIN, Log.INFO)) {
            Log.i(LOG_TAG_MAIN, String.format("PMZ description '%s' loading time is %sms", fileName, Long.toString((new Date().getTime() - startTimestamp.getTime()))));
        }
        return model;
    }


    public static City importPmz(String filename) throws IOException {
        SubwayMapBuilder subwayMapBuilder = new SubwayMapBuilder();
        SubwayMap subwayMap = subwayMapBuilder.importPmz(filename);
        City model = new City();
        model.subwayMap = subwayMap;
        return model;
    }

}
