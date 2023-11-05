package com.idorsia.research.osiris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idorsia.research.osiris.data.BatchDto;
import com.idorsia.research.osiris.data.CompoundDto;
import com.idorsia.research.osiris.data.EmployeeGroupDto;
import com.idorsia.research.osiris.data.EmployeeDto;
import com.idorsia.research.osiris.data.JsonUtils;
import com.idorsia.research.osiris.data.ProjectDto;
import com.idorsia.research.osiris.data.SampleDto;
import com.idorsia.research.osiris.data.SendTerminologyDto;
import com.idorsia.research.osiris.data.SubstanceDto;

public class OsirisService {
    private static final String LABINVENTORY_SERVICE_HOST = "ares";
    private static final String LABINVENTORY_SERVICE_HOST_TEST = "apollo";
    private static final String LABINVENTORY_SERVICE_HOST_DEV = "localhost";
    private static final String LABINVENTORY_SERVICE_PORT = "9981";
    private static final String LABINVENTORY_CONTEXT_PATH = "osiris-ws";
    private static final String SPACECHAR = "%20";

    private static String responseMessage = "";

    private static String getOsirisServiceUri() {
        String mode = System.getProperty("mode");
        String production = System.getProperty("production");
        //mode="dev";
        if ("production".equals(mode) || "true".equals(production)) {
            return "http://" + LABINVENTORY_SERVICE_HOST + ":" + LABINVENTORY_SERVICE_PORT + "/" + LABINVENTORY_CONTEXT_PATH;
        } else if ("dev".equals(mode)) {
            return "http://" + LABINVENTORY_SERVICE_HOST_DEV + ":" + LABINVENTORY_SERVICE_PORT + "/" + LABINVENTORY_CONTEXT_PATH;
        } else {
            return "http://" + LABINVENTORY_SERVICE_HOST_TEST + ":" + LABINVENTORY_SERVICE_PORT + "/" + LABINVENTORY_CONTEXT_PATH;
        }
    }

    /** PROJECTS **/
    
    public static List<ProjectDto> getAllProjects() {
        List<ProjectDto> projects = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/project/all");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF8")))
                        .lines().collect(Collectors.joining("\n"));

                JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "projects");
                if (jsonNode != null) {
                    String projectsData = jsonNode.toString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    projects = objectMapper.readValue(projectsData, new TypeReference<List<ProjectDto>>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }
    
    public static ProjectDto getProjectByName(String projectName) {
    	ProjectDto project = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/project/"+projectName);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String projectData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                if(projectData!=null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    project = objectMapper.readValue(projectData, new TypeReference<ProjectDto>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return project;
    }
    
    public static List<ProjectDto> getProjectsByResponsible(String responsible) {
    	List<ProjectDto> projects = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/project/projects/"+responsible);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                projects = objectMapper.readValue(jsonData, new TypeReference<List<ProjectDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;        
    }
    
    public static boolean addProject(ProjectDto projectDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(getOsirisServiceUri() + "/project/add");
        try {
            String jsonData = JsonUtils.mapToJson(projectDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));

                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Project successfuly added.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    /*public static boolean updateProject(ProjectDto projectDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(getOsirisServiceUri() + "/project/update");
        try {
            String jsonData = JsonUtils.mapToJson(projectDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPut.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Project successfuly updated.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteProject(String projectName) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(getOsirisServiceUri() + "/project/delete/" + projectName);
        try {
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "message");
                        if (jsonNode != null) {
                            responseMessage = jsonNode.toString();
                        }
                    }
                }
                return false;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }*/
    
    /** COMPOUNDS **/
    
    public static CompoundDto getCompoundByChemLabJournal(String chemLabJournal) {
    	CompoundDto compound = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/compound/"+chemLabJournal);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String compoundData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                if(compoundData!=null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    compound = objectMapper.readValue(compoundData, new TypeReference<CompoundDto>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compound;
    }
    
    public static List<CompoundDto> getCompoundsByCompoundName(String compoundName) {
    	List<CompoundDto> compounds = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/compound/compounds/"+compoundName);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                compounds = objectMapper.readValue(jsonData, new TypeReference<List<CompoundDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compounds;        
    }
    
    /** SUBSTANCES **/    
    
    public static List<String> getAllSubstances() {
        List<String> substances = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/substance/all");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF8")))
                        .lines().collect(Collectors.joining("\n"));

                JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "substances");
                if (jsonNode != null) {
                    String substancesData = jsonNode.toString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    substances = objectMapper.readValue(substancesData, new TypeReference<List<String>>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return substances;
    }
    
    public static SubstanceDto getSubstanceByName(String actNo) {
    	SubstanceDto substance = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/substance/"+actNo);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String substanceData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                if(substanceData!=null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    substance = objectMapper.readValue(substanceData, new TypeReference<SubstanceDto>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return substance;
    }
    
    public static List<SubstanceDto> getSubstancesByFormula(String formula) {
    	List<SubstanceDto> substances = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/substance/substances/"+formula);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                substances = objectMapper.readValue(jsonData, new TypeReference<List<SubstanceDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return substances;        
    }
    
    public static boolean addSubstance(SubstanceDto substanceDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(getOsirisServiceUri() + "/substance/add");
        try {
            String jsonData = JsonUtils.mapToJson(substanceDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));

                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Substance successfuly added.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    /*public static boolean updateSubstance(SubstanceDto substanceDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(getOsirisServiceUri() + "/substance/update");
        try {
            String jsonData = JsonUtils.mapToJson(substanceDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPut.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Substance successfuly updated.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteSubstance(String actNo) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(getOsirisServiceUri() + "/substance/delete/" + actNo);
        try {
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "message");
                        if (jsonNode != null) {
                            responseMessage = jsonNode.toString();
                        }
                    }
                }
                return false;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }*/
    
    /** BATCHES **/
    
    public static List<String> getAllBatches() {
        List<String> batches = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/batch/all");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF8")))
                        .lines().collect(Collectors.joining("\n"));

                JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "batches");
                if (jsonNode != null) {
                    String batchesData = jsonNode.toString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    batches = objectMapper.readValue(batchesData, new TypeReference<List<String>>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batches;
    }
    
    public static BatchDto getBatchByChemLabJournal(String chemLabJournal) {
    	BatchDto batch = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/batch/"+chemLabJournal);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String batchData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                if(batchData!=null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    batch = objectMapper.readValue(batchData, new TypeReference<BatchDto>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batch;
    }
    
    public static List<BatchDto> getBatchesByActNo(String actNo) {
    	List<BatchDto> batches = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/batch/batches/actNo/"+actNo);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                batches = objectMapper.readValue(jsonData, new TypeReference<List<BatchDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batches;        
    }
    
    public static List<BatchDto> getBatchesByChemist(String chemist) {
    	List<BatchDto> batches = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/batch/batches/chemist/"+chemist);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                batches = objectMapper.readValue(jsonData, new TypeReference<List<BatchDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batches;        
    }
    
    public static List<BatchDto> getBatchesByProducer(String producer) {
    	List<BatchDto> batches = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/batch/batches/producer/"+producer);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                batches = objectMapper.readValue(jsonData, new TypeReference<List<BatchDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batches;        
    }
    
    public static List<BatchDto> getBatchesByProject(String projectName) {
    	List<BatchDto> batches = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/batch/batches/project/"+projectName);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                batches = objectMapper.readValue(jsonData, new TypeReference<List<BatchDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batches;        
    }
    
    public static List<BatchDto> getBatchesBySampleExternalReference(String externalReference) {
    	List<BatchDto> batches = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/batch/batches/sample/"+externalReference);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                batches = objectMapper.readValue(jsonData, new TypeReference<List<BatchDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batches;        
    }    
    public static boolean addBatch(BatchDto batchDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(getOsirisServiceUri() + "/batch/add");
        try {
            String jsonData = JsonUtils.mapToJson(batchDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));

                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Batch successfuly added.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    /*public static boolean updateBatch(BatchDto batchDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(getOsirisServiceUri() + "/batch/update");
        try {
            String jsonData = JsonUtils.mapToJson(batchDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPut.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Batch successfuly updated.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteBatch(String chemLabJournal) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(getOsirisServiceUri() + "/batch/delete/" + chemLabJournal);
        try {
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "message");
                        if (jsonNode != null) {
                            responseMessage = jsonNode.toString();
                        }
                    }
                }
                return false;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }*/    
    
    /** SAMPLES **/
    
    public static List<String> getAllSamples() {
        List<String> samples = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/all");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF8")))
                        .lines().collect(Collectors.joining("\n"));

                JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "samples");
                if (jsonNode != null) {
                    String samplesData = jsonNode.toString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    samples = objectMapper.readValue(samplesData, new TypeReference<List<String>>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }
    
    public static SampleDto getSampleBySampleId(Integer sampleId) {
    	SampleDto sample = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/"+sampleId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String sampleData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                if(sampleData!=null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    sample = objectMapper.readValue(sampleData, new TypeReference<SampleDto>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sample;
    }
    
    public static List<SampleDto> getSamplesByActNo(String actNo) {
    	List<SampleDto> samples = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/samples/actNo/"+actNo);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                samples = objectMapper.readValue(jsonData, new TypeReference<List<SampleDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;        
    }
    
    public static List<SampleDto> getSamplesByExtReference(String extReference) {
    	List<SampleDto> samples = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/samples/extReference/"+extReference);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                samples = objectMapper.readValue(jsonData, new TypeReference<List<SampleDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;        
    }
    
    public static List<SampleDto> getSamplesBySupplier(String supplier) {
    	List<SampleDto> samples = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/samples/supplier/"+supplier);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                samples = objectMapper.readValue(jsonData, new TypeReference<List<SampleDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;        
    }
    
    public static List<SampleDto> getSamplesByCatalogNo(String catalogNo) {
    	List<SampleDto> samples = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/samples/catalogNo/"+catalogNo);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                samples = objectMapper.readValue(jsonData, new TypeReference<List<SampleDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;        
    }
    
    public static List<SampleDto> getSamplesByLocation(String location) {
    	List<SampleDto> samples = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/samples/location/"+location);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                samples = objectMapper.readValue(jsonData, new TypeReference<List<SampleDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;        
    }
    
    public static List<SampleDto> getSamplesByUserId(String userId) {
    	List<SampleDto> samples = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/sample/samples/user/"+userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                samples = objectMapper.readValue(jsonData, new TypeReference<List<SampleDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;        
    }
    
    public static boolean addSample(SampleDto sampleDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(getOsirisServiceUri() + "/sample/add");
        try {
            String jsonData = JsonUtils.mapToJson(sampleDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));

                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Sample successfuly added.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    /*public static boolean updateSample(SampleDto sampleDto) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(getOsirisServiceUri() + "/sample/update");
        try {
            String jsonData = JsonUtils.mapToJson(sampleDto);
            HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            httpPut.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        System.out.println("Sample successfuly updated.");
                    }
                } else {
                    System.out.println("No Response.");
                }
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteSample(Integer sampleId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(getOsirisServiceUri() + "/sample/delete/" + sampleId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                if (response.getEntity() != null) {
                    String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                            .lines().collect(Collectors.joining("\n"));
                    if (jsonResponse != null && !jsonResponse.isEmpty()) {
                        JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "message");
                        if (jsonNode != null) {
                            responseMessage = jsonNode.toString();
                        }
                    }
                }
                return false;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }*/       
    
    /** Employee **/
    
    public static List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employees = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/employee/all");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF8")))
                        .lines().collect(Collectors.joining("\n"));

                JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "employees");
                if (jsonNode != null) {
                    String employeessData = jsonNode.toString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    employees = objectMapper.readValue(employeessData, new TypeReference<List<EmployeeDto>>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    public static EmployeeDto getEmployeeById(Integer id) {
    	EmployeeDto employee = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/employee/"+id);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String employeeData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                if(employeeData!=null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    employee = objectMapper.readValue(employeeData, new TypeReference<EmployeeDto>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employee;
    }    
    
    public static EmployeeDto getEmployeeByUserId(String userId) {
    	EmployeeDto employee = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/employee/user/"+userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String employeeData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                if(employeeData!=null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    employee = objectMapper.readValue(employeeData, new TypeReference<EmployeeDto>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employee;
    } 
    
    public static List<EmployeeDto> getEmployeesByManagerId(Integer managerId) {
    	List<EmployeeDto> employees = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/employee/employees/manager/"+managerId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                employees = objectMapper.readValue(jsonData, new TypeReference<List<EmployeeDto>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;        
    }
    
    public static List<String> getScientificEmployees() {
    	List<String> employees = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/employee/scientific_people/");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                employees = objectMapper.readValue(jsonData, new TypeReference<List<String>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;        
    }
    
    public static List<String> getGroupsByUserId(String userId) {
    	List<String> groups = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/employee/groups/"+userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                groups = objectMapper.readValue(jsonData, new TypeReference<List<String>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groups;        
    }
    
    public static List<String> getGroupsIdsByUserId(String userId) {
    	List<String> groups = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/employee/groups/ids/"+userId);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                groups = objectMapper.readValue(jsonData, new TypeReference<List<String>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groups;        
    }
    
   /** SEND TERMINOLOGY **/
    
    public static List<SendTerminologyDto> getAllTerminolgy() {
        List<SendTerminologyDto> terminology = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/send_terminology/all");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF8")))
                        .lines().collect(Collectors.joining("\n"));

                JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "terminology");
                if (jsonNode != null) {
                    String terminologyData = jsonNode.toString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    terminology = objectMapper.readValue(terminologyData, new TypeReference<List<SendTerminologyDto>>() {
                    });
                }
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return terminology;
    }
    
    public static List<String> getTerminologyByCode(String code) {
    	List<String> terminology = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/send_terminology/code/"+code);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String terminologyData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                terminology = objectMapper.readValue(terminologyData, new TypeReference<List<String>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return terminology;        
    }
    
    public static List<String> getSendTerminologyCodeListNames() {
    	List<String> codeNames = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/send_terminology/codelistname/");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String codeNamesData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                codeNames = objectMapper.readValue(codeNamesData, new TypeReference<List<String>>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return codeNames;        
    }
    
    public static String getSendTerminologyCodeByCodeName(String codeName) {
    	String code = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(codeName.contains(" ")) codeName = StringUtils.replace(codeName, " ", SPACECHAR);
        HttpGet httpGet = new HttpGet(getOsirisServiceUri() + "/send_terminology/codename/"+codeName);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getEntity() != null) {
                String codeData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                ObjectMapper objectMapper = new ObjectMapper();
                code = objectMapper.readValue(codeData, new TypeReference<String>() {
                });
            } else {
                System.out.println("No Response.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;        
    }
    

    public static String getResponseMessage() {
        String ret = responseMessage;
        responseMessage = "";
        return ret;
    }
    
    public static void main(String[] args) {
    	System.out.println(getAllProjects().size());
    }
}