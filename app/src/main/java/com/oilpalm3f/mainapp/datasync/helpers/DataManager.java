package com.oilpalm3f.mainapp.datasync.helpers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//data base strings are defined here
public class DataManager<T> {

    private static final String LOG_TAG = DataManager.class.getName();

    private static final DataManager instance = new DataManager();
    public static final String TransportServiceQuestioner="TransportServiceQuestioner";

    public static final String YIELD_ASSESSMENT="yieldassesment";
    public static final String WHITE_FLY="white_fly";
    public static final String WHITE_FLY_18="white_fly_18";
    public static final String WHITE_FLY_19="white_fly_19";

    public static final String COLLECTION_CENTER_DATA = "collection_center_data";
    public static final String SELECTED_FARMER_DATA = "selected_farmer_data";
    public static final String SELECTED_FARMER_PLOT_DATA = "selected_farmer_plot_data";
    public static final String EXTRA_SELECTED_FARMER_PLOT_DATA = "extra_selected_farmer_plot_data";
    public static final String USER_DETAILS = "user_details";
    public static final String USER_VILLAGES = "user_villages";
    public static final String MILL_INFORMATION = "mill_information";
    public static final String PRIVATE_WEIGHBRIDGE_INFO = "private_weighbridge_info";
    public static final String TOTAL_FARMERS_DATA = "total_farmers_data";
    public static final String EXTRA_PLOTS = "extra_plots";
    public static final String FARMER_PERSONAL_DETAILS = "farmer_personal_details";
    public static final String SOURCE_OF_WATER = "source_of_water";
    public static final String FERTILIZER = "fertilizer";
    public static final String RECMND_FERTILIZER = "recmnd_fertilizer";
    public static final String RECMND_NUTRIENT = "recmnd_nutrient";

    public static final String PlotCurrent_Crop_Conversion = "PlotCurrent_Crop_Conversion";
    public static final String RECMND_PEST_DETAILS = "recmnd_pest_details_screen";
    public static final String RECMND_NUTRIENT_DETAILS_SCREEN = "recmnd_nutrient_details_screen";





    public static final String CURRENT_PLANTATION = "currentPlantation";
    public static final String PEST_DETAILS = "pest";
    public static final String MAIN_PEST_DETAIL = "main_pest_detail";
    public static final String DISEASE_DETAILS = "disease";
    public static final String NUTRIENT_DETAILS = "nutrient";
    public static final String COMPLAINT_DETAILS = "complaint";
    public static final String WEEDING_DETAILS = "weeding";
    public static final String WEEDING_HEALTH_OF_PLANTATION_DETAILS = "healthOfPlantation";
    public static final String FFB_HARVEST_DETAILS = "ffbHarvest";
    public static final String CHEMICAL_DETAILS = "chemical";
    public static final String SoilType = "soil_type";
    public static final String TypeOfIrrigation = "type_of_irrigation";
    public static final String FARMER_ADDRESS_DETAILS = "farmer_address_details";
    public static final String FILE_REPOSITORY = "FileRepository";
    public static final String FILE_REPOSITORY_PLANTATION = "FileRepositoryPlantation";
    public static final String HOP_FILE_REPOSITORY_PLANTATION = "HoPFileRepositoryPlantation";
    public static final String CP_FILE_REPOSITORY_PLANTATION = "FileRepositoryConversionPlantation";
    public static final String PLOT_ADDRESS_DETAILS = "plot_address_details";
    public static final String PLOT_DETAILS = "plot_details";
    public static final String VALIDATE_PLOT_ADDRESS_DETAILS = "validate_plot_address_details";
    public static final String FARMER_CONVERSION_POTENTIAL = "farmer_conversion_potential";
    public static final String PLOT_NEIGHBOURING_PLOTS_DATA = "plot_neighbouring_plots_data";
    public static final String PLOT_INTER_CROP_DATA = "plot_inter_crop_data";
    public static final String PLOT_CURRENT_CROPS_DATA = "plot_current_crops_data";
    public static final String LANDLORD_LEASED_DATA="landlord_leased_data";
    public static final String LANDLORD_BANK_DATA="landlord_bank_data";
    public static final String LANDLORD_IDPROOFS_DATA="landlord_idproofs_data";

    public static final String PLOT_NEIGHBOURING_PLOTS_DATA_PAIR = "plot_neighbouring_plots_data_pair";
    public static final String PLOT_CURRENT_CROPS_DATA_PAIR = "plot_current_crops_data_pair";
    public static final String PLOT_GEO_TAG = "plot_geo_tag";
    public static final String PLOT_FOLLOWUP = "plot_followup";
    public static final String REFERRALS_DATA = "referrals_data";
    public static final String MARKET_SURVEY_DATA = "market_survey_data";
    public static final String OIL_TYPE_MARKET_SURVEY_DATA = "oil_type_market_survey_data";
    public static final String ID_PROOFS_DATA = "id_proofs_data";
    public static final String FARMER_BANK_DETAILS = "farmer_bank_details";
    public static final String PLOT_INTER_CROP_DATA_PAIR = "plot_inter_crop_data_pair";
    public static final String PLANTATION_CON_DATA = "plantation_con_data";
    public static final String USER_ACTIVITY_RIGHTS = "user_activity_rights";
    public static final String COMPLAINT_REPOSITORY = "complaint_repository";
    public static final String COMPLAINT_STATUS_HISTORY = "complaint_status_history";
    public static final String COMPLAINT_TYPE= "complaint_xref";

    public static final String IS_FARMER_DATA_UPDATED = "is_farmer_data_updated";
    public static final String IS_PLOT_DATA_UPDATED = "is_Plot_data_updated";
    public static final String IS_PLOTS_DATA_UPDATED = "is_plots_data_updated";
    public static final String IS_WOP_DATA_UPDATED = "is_wop_data_updated";

    public static final String NEW_COMPLAINT_DETAILS = "new_co mplaint";
    public static final String NEW_COMPLAINT_STATUS_HISTORY = "new_complaint_status_history";
    public static final String NEW_COMPLAINT_REPOSITORY = "new_complaint_repository";
    public static final String NEW_COMPLAINT_TYPE = "new_complaint_xref";


    private final Map<String, T> dataMap = new ConcurrentHashMap<>();

    public static DataManager getInstance() {
        return instance;
    }

    public synchronized void addData(final String type, final T data) {
        dataMap.put(type, data);
    }

    public synchronized T getDataFromManager(final String type) {
        return dataMap.get(type);
    }

    public void deleteData(final String type) {
        if (dataMap.get(type) != null) {
            dataMap.remove(type);
        }
    }
}
