/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cloudstack.storage.image.store;

import javax.inject.Inject;

import org.apache.cloudstack.storage.image.Template;
import org.apache.cloudstack.storage.image.db.ImageDataDao;
import org.apache.cloudstack.storage.image.db.ImageDataVO;
import org.apache.cloudstack.storage.image.downloader.ImageDownloader;
import org.apache.cloudstack.storage.image.driver.ImageDataStoreDriver;

public class ImageDataStoreImpl implements ImageDataStore {
	@Inject
	ImageDataDao imageDao;
	ImageDataStoreDriver driver;
	ImageDownloader downloader;
	boolean needDownloadToCacheStorage = false;
	
	
	public ImageDataStoreImpl(ImageDataStoreDriver driver, boolean needDownloadToCacheStorage, ImageDownloader downloader) {
		this.driver = driver;
		this.needDownloadToCacheStorage = needDownloadToCacheStorage;
		this.downloader = downloader;
	}
	
	@Override
	public Template registerTemplate(long templateId) {
		ImageDataVO idv = imageDao.findById(templateId);
		Template template = new Template(this, idv);
		if (driver.registerTemplate(template)) {
			return template;
		} else {
			return null;
		}
	}

	@Override
	public String grantAccess(long templateId, long endPointId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean revokeAccess(long templateId, long endPointId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTemplate(long templateId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needDownloadToCacheStorage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDownloader getImageDownloader() {
		return this.downloader;
	}

}
