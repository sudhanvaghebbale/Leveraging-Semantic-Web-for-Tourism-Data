#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Nov 24 22:00:30 2020

@author: sudhanvahebbale
"""

import pandas as pd
import requests
import random
import collections
 
def populatePOI(file):
    POI = pd.read_csv(file)
    details = POI['details']
    reviews = POI['reviews']
    details.dropna()
    reviews.dropna()
    
    POIDataFrame = pd.DataFrame()
    statisticsDataFrame = pd.DataFrame()
    reviewDataFrame = pd.DataFrame()
    subCategoryDataFrame = pd.DataFrame()
    reviewPOIMap = pd.DataFrame()
    statisticsPOIMap = pd.DataFrame()
    subCategoryPOIMap = pd.DataFrame()
    subCategoriesDict = collections.defaultdict(str)
    
    count = 0
    for index, row in details.iteritems():
        if index == 1000:
            break

        try:
            flag = 0
            json = requests.get(row).json()
            row = json['statistics']
            category = json['subCategory']
            reviewJSON = requests.get(reviews.iloc[index]).json()
            POIDataFrame = POIDataFrame.append(json, ignore_index=True)
            flag = 1
            currentPOIID = POIDataFrame['id'].iloc[-1]
        except:
            if flag == 1:
                POIDataFrame = POIDataFrame[:-1]
            continue
        
        
        if category not in subCategoriesDict:
            subCategoryDataFrame = subCategoryDataFrame.append({'ID': 'SUBCATLON' + str(len(subCategoriesDict)+1), 'Name': category},
                                                               ignore_index=True)
            string = 'SUBCATLON' + str(len(subCategoriesDict)+1)
            subCategoriesDict[category] = string
        
        subCategoryPOIMap = subCategoryPOIMap.append({'POI_ID': currentPOIID, 
                                                     'SUBCAT_ID': subCategoriesDict[category]}, 
                                                     ignore_index=True)
        
        
        
        row['Foursquare'].update({'ID': 'STATLON' + str(index+1)}) 
        del row['Foursquare']['hereNow'], row['Foursquare']['price']
        statisticsDataFrame = statisticsDataFrame.append(row['Foursquare'], ignore_index=True)
        
        
        for review in reviewJSON:
            count += 1
            del review['details']
            review.update({'ID': 'REVLON' + str(count+1)})
            reviewDataFrame = reviewDataFrame.append(review, ignore_index=True)
            reviewPOIMap = reviewPOIMap.append({'REV_ID': 'REVLON' + str(count), 'POI_ID': currentPOIID}, 
                                                   ignore_index=True)
    
    reviewPOIMap = reviewPOIMap[['REV_ID', 'POI_ID']]
    statisticsPOIMap['STAT_ID'] = statisticsDataFrame['ID']
    statisticsPOIMap['POI_ID'] = POIDataFrame['id']
    
    POIDataFrame['popularityIndex'] = [random.randint(5, 10) for i in range(len(POIDataFrame['id']))]
    POIDataFrame['covidSafetyRating'] = [random.randint(1, 5) for i in range(len(POIDataFrame['id']))]
    
    POIDataFrame = POIDataFrame.drop(columns=['category', 'subCategory', 'location', 'statistics', 'description', 
                                              'external_urls', 'icon', 'international_phone_number', 'phone_number',
                                              'originalId', 'services', 'reviews'])
    

    writer = pd.ExcelWriter('POI_Ontology.xlsx', engine='xlsxwriter')
    POIDataFrame.to_excel(writer, sheet_name='POI', index=False)
    subCategoryDataFrame.to_excel(writer, sheet_name='SubCategory', index=False)
    reviewDataFrame.to_excel(writer, sheet_name='Review', index=False)
    statisticsDataFrame.to_excel(writer, sheet_name='Foursquare', index=False)
    subCategoryPOIMap.to_excel(writer, sheet_name='SubCategoryMap', index=False)
    reviewPOIMap.to_excel(writer, sheet_name='ReviewMap', index=False)
    statisticsPOIMap.to_excel(writer, sheet_name='FoursquareMap', index=False)
    writer.save()


def populatePubs(file):
    pubsDataFrame = pd.read_excel(file)
     
    popularItemDataFrame = pd.read_excel('Pubs.xlsx', sheet_name='Sheet4', names=('ID', 'Name', 'Category', 'Serves'))
    popularItemDataFrame = popularItemDataFrame[1:]
    
    count = 0
    postcodeDataFrame = pd.DataFrame()
    postcodePubMap = pd.DataFrame()
    postcodes = collections.defaultdict(str)
    for index, row in pubsDataFrame['Postcode'].iteritems():
        if row not in postcodes:
            postcodeDataFrame = postcodeDataFrame.append({'ID': 'POSTLON' + str(count+1), 
                                                                          'Value': row}, ignore_index=True)
            postcodes[row] = 'POSTLON' + str(count+1)
            count += 1
        postcodePubMap = postcodePubMap.append({'PubID': pubsDataFrame['ID'][index],
                                                'POSTID': postcodes[row]}, ignore_index=True)
    postcodePubMap = postcodePubMap[['PubID', 'POSTID']]
    
    count = 0
    localAuthorityDataFrame = pd.DataFrame()
    localAuthorityPubMap = pd.DataFrame()
    boroughs = collections.defaultdict(str)
    for index, row in pubsDataFrame['Local_Authority'].iteritems():
        if row not in boroughs:
            localAuthorityDataFrame = localAuthorityDataFrame.append({'ID': 'LOCLON' + str(count+1), 
                                                                          'Name': row}, ignore_index=True)
            boroughs[row] = 'LOCLON' + str(count+1)
            count += 1
        localAuthorityPubMap = localAuthorityPubMap.append({'PubID': pubsDataFrame['ID'][index],
                                                'LOCID': boroughs[row]}, ignore_index=True)
    localAuthorityPubMap = localAuthorityPubMap[['PubID', 'LOCID']]
    
    pubsDataFrame['Rating'] = [random.randint(2, 5) for i in range(len(pubsDataFrame['ID'])) ]
    pubsDataFrame = pubsDataFrame.drop(columns=['Postcode', 'Easting', 'Northing', 'Local_Authority', 'Unnamed: 9'])
    
    pubsDataFrame['popularityIndex'] = [random.randint(5, 10) for i in range(len(pubsDataFrame['ID']))]
    pubsDataFrame['covidSafetyRating'] = [random.randint(1, 5) for i in range(len(pubsDataFrame['ID']))]
    
    
    pubsMenuMap = pd.DataFrame()
    items = popularItemDataFrame['ID'].to_list()
    for index, row in pubsDataFrame['ID'].iteritems():
        menuCount = random.randint(2, 4)
        menuItems = []
        for i in range(menuCount):
            item = random.choice(items)
            if item not in menuItems:
                menuItems.append(item)
        
        pubsMenuMap = pubsMenuMap.append({'ID': row, 'MenuID': menuItems}, ignore_index=True)
    
    pubsMenuMap = (pubsMenuMap.set_index(['ID'])['MenuID']
                         .apply(pd.Series)
                         .stack()
                         .reset_index()
                         .drop('level_1', axis=1)
                         .rename(columns={0:'MenuID'}))
    
    writer = pd.ExcelWriter('Pubs_Ontology.xlsx', engine='xlsxwriter')
    pubsDataFrame.to_excel(writer, sheet_name='Pubs', index=False)
    postcodeDataFrame.to_excel(writer, sheet_name='PostCode', index=False)
    localAuthorityDataFrame.to_excel(writer, sheet_name='Local Authority', index=False)
    popularItemDataFrame.to_excel(writer, sheet_name='PopularItems', index=False)
    postcodePubMap.to_excel(writer, sheet_name='Pub_PostCode', index=False)
    localAuthorityPubMap.to_excel(writer, sheet_name='Pub_LocalAuthority', index=False)
    pubsMenuMap.to_excel(writer, sheet_name='PubsMenu', index=False)
    writer.save()
    

def populateStations(file):
    stationsDataFrame = pd.read_csv(file)
    stationsDataFrame = stationsDataFrame.dropna()
    stationsDataFrame.drop(columns=['OS X', 'OS Y'])
    
    stationsID = ['STALON' + str(i) for i in range(1, len(stationsDataFrame['Station'])+1)]
    stationsDataFrame.insert(loc=0, column='ID', value=stationsID)
    stationsDataFrame['PlatformCount'] = [random.randint(2, 8) for i in range(len(stationsDataFrame['ID']))]  
    stationsDataFrame['WheelChairAccessible'] = [random.choice(['Yes', 'No']) 
                                                 for i in range(len(stationsDataFrame['ID']))]
    
    stationsDataFrame['Zone'] = stationsDataFrame['Zone'].astype(str)
    stationsDataFrame['Zone'] = stationsDataFrame['Zone'].str.split(",")
    
    stationsDataFrame = (stationsDataFrame.set_index(['ID', 'Station', 'Latitude', 'Longitude', 'PlatformCount', 
                                                      'WheelChairAccessible'])['Zone']
                         .apply(pd.Series)
                         .stack()
                         .reset_index()
                         .drop('level_6', axis=1)
                         .rename(columns={0:'Zone'}))
    
    stationsDataFrame = stationsDataFrame.dropna()
    zonesDataFrame = pd.read_excel('Stations__.xlsx', sheet_name='Sheet4', names=('ID', 'Number', 'Color'))
    trainsDataFrame = pd.read_excel('Stations__.xlsx', sheet_name='Sheet2', names=('ID', 'Zone', 
                                                                          'Name', 'StartTime',
                                                                          'EndTime', 'Capacity'))

    stationZoneMap = stationsDataFrame['ID'].to_frame()
    stationZoneMap['Zone_ID'] = stationsDataFrame['Zone']
    
    stationsDataFrame['popularityIndex'] = [random.randint(5, 10) for i in range(len(stationsDataFrame['ID']))]
    stationsDataFrame['covidSafetyRating'] = [random.randint(1, 5) for i in range(len(stationsDataFrame['ID']))]
    
    zones = {}
    for i in range(1, 10):
        zones[str(i)] = 'STZLON' + str(i-1)
    
    stationZoneMap['Zone_ID'] = stationZoneMap['Zone_ID'].map(zones)
    stationsDataFrame = stationsDataFrame.drop(columns=['Zone'])
    
    writer = pd.ExcelWriter('Stations_Ontology.xlsx', engine='xlsxwriter')
    stationsDataFrame.to_excel(writer, sheet_name='Stations', index=False)
    zonesDataFrame.to_excel(writer, sheet_name='Zone', index=False)
    trainsDataFrame.to_excel(writer, sheet_name='Trains', index=False)
    stationZoneMap.to_excel(writer, sheet_name='StationZoneMap', index=False)
    writer.save()
    
if __name__ == '__main__':
    populatePOI('london-poi.csv')
    populatePubs('Boroughs.xlsx')
    populateStations('London_Stations.csv')
