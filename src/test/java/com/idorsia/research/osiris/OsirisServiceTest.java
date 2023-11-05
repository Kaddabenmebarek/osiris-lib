package com.idorsia.research.osiris;


import org.junit.Assert;
import org.junit.Test;

import com.idorsia.research.osiris.data.BatchDto;
import com.idorsia.research.osiris.data.CompoundDto;
import com.idorsia.research.osiris.data.EmployeeDto;
import com.idorsia.research.osiris.data.EmployeeGroupDto;
import com.idorsia.research.osiris.data.ProjectDto;
import com.idorsia.research.osiris.data.SampleDto;
import com.idorsia.research.osiris.data.SendTerminologyDto;
import com.idorsia.research.osiris.data.SubstanceDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OsirisServiceTest {
	
	/** PROJECTS **/
    @Test
    public void testGetAllProjects() {
        List<ProjectDto> projects = OsirisService.getAllProjects();        
        Assert.assertTrue(!projects.isEmpty());
    }

    @Test
    public void testGetProject() {
    	String projectName = "P2X7";
        ProjectDto projectDto = OsirisService.getProjectByName(projectName);
        Assert.assertNotNull(projectDto);
        Assert.assertTrue(projectDto.getProjectName().equals("P2X7"));
    }

    @Test
    public void testGetProjectsByResponsible() {
    	String responsible = "LEROYX";
        List<ProjectDto> projects = OsirisService.getProjectsByResponsible(responsible);
        Assert.assertTrue(!projects.isEmpty());
        Assert.assertTrue(projects.get(0).getResponsible().equals("LEROYX"));
    }

    @Test
    public void testAddProject() {
    	ProjectDto proj = new ProjectDto();
		proj.setProjectName("Project-XXX");
		proj.setShortName("PRJXXX");
		proj.setThemeNo(String.valueOf(17));
		proj.setResponsible("BENMEKA1");
		proj.setCmnt("XXX Project Analysis");
		proj.setUserId("BENMEKA1");
		proj.setCategory("General");
		proj.setRelevantTestId(850);
		proj.setInitiated(new Date());
		proj.setUpdDate(new Date());
    	OsirisService.addProject(proj);
    	ProjectDto savedProject = OsirisService.getProjectByName("Project-XXX");
    	Assert.assertNotNull(savedProject);
    }
    
    
    /** COMPOUNDS **/
    @Test
    public void testGetCompoundByChemLabJournal() {
    	String chemLabJournal = "ADM-12403783";
    	CompoundDto compoundDto = OsirisService.getCompoundByChemLabJournal(chemLabJournal);
        Assert.assertNotNull(compoundDto);
        Assert.assertTrue(compoundDto.getChemLabJournal().equals("ADM-12403783"));
    }
    
    @Test
    public void testGetCompoundsByCompoundName() {
    	String compoundName = "ACT-000001";
        List<CompoundDto> compounds = OsirisService.getCompoundsByCompoundName(compoundName);
        Assert.assertTrue(!compounds.isEmpty());
        Assert.assertTrue(compounds.get(0).getActNo().equals("ACT-000001"));
    }
    
    /** SUBSTANCE **/
    @Test
    public void testGetAllSubstances() {
        List<String> substances = OsirisService.getAllSubstances();        
        Assert.assertTrue(!substances.isEmpty());
    }
    
    @Test
    public void testGetSubstanceByName() {
    	String actNo = "ACT-053389";
    	SubstanceDto substanceDto = OsirisService.getSubstanceByName(actNo);
        Assert.assertNotNull(substanceDto);
        Assert.assertTrue(substanceDto.getActNo().equals("ACT-053389"));
    }
    
    @Test
    public void testGetSubstancesByFormula() {
    	String formula = "CH6N4";
        List<SubstanceDto> substances = OsirisService.getSubstancesByFormula(formula);
        Assert.assertTrue(!substances.isEmpty());
        Assert.assertTrue(substances.get(0).getFormula().equals("CH6N4"));
    }

    @Test
    public void testAddSubstance() {
    	SubstanceDto substance = new SubstanceDto();
    	substance.setActNo("IDOR-XXX");
        substance.setFormula("F-IDORXXX");
        substance.setMolWeight(new BigDecimal(200.843));
        substance.setAbsWeight(new BigDecimal(199.728));
        substance.setSystematicName("Carbonic acid");
        substance.setKnown('N');
        substance.setKnownSource(null);
        substance.setSubstanceClass(null);
        substance.setMolFile("azertyuiopqsdfghjklmwxcvbn");
        substance.setCmnt(null);
        substance.setUserId("BENMEKA1");
        substance.setUpdDate(new Date());
        substance.setClogp(new BigDecimal(0.43));
        substance.setIdCode("azerty@qwerty@@");
        substance.setCoordinates("!BjdEOBc`tRPA`EP");
        substance.setTautomer(4649735955658549534L);
        substance.setFlag(0);
    	OsirisService.addSubstance(substance);
    	SubstanceDto savedSubstance = OsirisService.getSubstanceByName("IDOR-XXX");
    	Assert.assertNotNull(savedSubstance);
    }
    
    /** BATCHES **/
    @Test
    public void testGetAllBatches() {
        List<String> batches = OsirisService.getAllBatches();        
        Assert.assertTrue(!batches.isEmpty());
    }
    
    @Test
    public void testGetBatchByChemLabJournal() {
    	String chemLabJournal = "LJC380-015";
    	BatchDto batchDto = OsirisService.getBatchByChemLabJournal(chemLabJournal);
        Assert.assertNotNull(batchDto);
        Assert.assertTrue(batchDto.getChemLabJournal().equals("LJC380-015"));
    }
    
    @Test
    public void testGetBatchesByActNo() {
    	String actNo = "ACT-004877";
        List<BatchDto> batches = OsirisService.getBatchesByActNo(actNo);
        Assert.assertTrue(!batches.isEmpty());
        Assert.assertTrue(batches.get(0).getActNo().equals("ACT-004877"));
    }
    
    @Test
    public void testGetBatchesByChemist() {
    	String chemist = "AANISPA1";
        List<BatchDto> batches = OsirisService.getBatchesByChemist(chemist);
        Assert.assertTrue(!batches.isEmpty());
        Assert.assertTrue(batches.get(0).getChemist().equals("AANISPA1"));
    }
    
    @Test
    public void testGetBatchesByProducer() {
    	String producer = "ABELES";
        List<BatchDto> batches = OsirisService.getBatchesByProducer(producer);
        Assert.assertTrue(!batches.isEmpty());
        Assert.assertTrue(batches.get(0).getProducer().equals("ABELES"));
    }
    
    @Test
    public void testGetBatchesByProject() {
    	String projectName = "IL4i1";
        List<BatchDto> batches = OsirisService.getBatchesByProject(projectName);
        Assert.assertTrue(!batches.isEmpty());
        Assert.assertTrue(batches.get(0).getProjectName().equals("IL4i1"));
    }
    
    @Test
    public void testGetBatchesBySampleExternalReference() {
    	String externalReference = "AA-015769-B1-2004";
        List<BatchDto> batches = OsirisService.getBatchesBySampleExternalReference(externalReference);
        Assert.assertTrue(!batches.isEmpty());
    }
    
    @Test
    public void testAddBatch() {
    	BatchDto batch = new BatchDto();
    	batch.setChemLabJournal("KB-XXX000");
        batch.setActNo("ACT-061560");
        batch.setChemist("BENMEKA1");
        batch.setProducer("EXTCOMP");
        batch.setProjectName("IL4i1");
        batch.setPreparationDate(new Date());
        batch.setPreparationDesc(null);
        batch.setPurification(null);
        batch.setMeltingPoint(0);
        batch.setBoilingPoint(0);
        batch.setColor(null);
        batch.setAmount(new BigDecimal(0.122));
        batch.setYield(new BigDecimal(23.22));
        batch.setCmnt(null);
        batch.setUserId("BENMEKA1");
        batch.setUpdDate(new Date());
        batch.setLocation("Compound Mgmt");
    	OsirisService.addBatch(batch);
    	BatchDto savedBatch = OsirisService.getBatchByChemLabJournal("KB-XXX000");
    	Assert.assertNotNull(savedBatch);
    }
    
    /** SAMPLES **/
    @Test
    public void testGetAllSamples() {
        List<String> samples = OsirisService.getAllSamples();        
        Assert.assertTrue(!samples.isEmpty());
    }
    
    @Test
    public void testGetSampleBySampleId() {
    	Integer sampleId = 10174388;
    	SampleDto sampleDto = OsirisService.getSampleBySampleId(sampleId);
        Assert.assertNotNull(sampleDto);
        Assert.assertTrue(sampleDto.getSampleId() == 10174388);
    }
    
    @Test
    public void testGetSamplesByActNo() {
    	String actNo = "ACT-287663";
        List<SampleDto> samples = OsirisService.getSamplesByActNo(actNo);
        Assert.assertTrue(!samples.isEmpty());
        Assert.assertTrue(samples.get(0).getActNo().equals("ACT-287663"));
    }
    
    @Test
    public void testGetSamplesByExtReference() {
    	String extReference = "EXT-1072-6334";
        List<SampleDto> samples = OsirisService.getSamplesByExtReference(extReference);
        Assert.assertTrue(!samples.isEmpty());
        Assert.assertTrue(samples.get(0).getExtReferfence().equals("EXT-1072-6334"));
    }
    
    @Test
    public void testGetSamplesBySupplier() {
    	String supplier = "Sigma-Aldrich";
        List<SampleDto> samples = OsirisService.getSamplesBySupplier(supplier);
        Assert.assertTrue(!samples.isEmpty());
        Assert.assertTrue(samples.get(0).getSupplier().equals("Sigma-Aldrich"));
    }
    
    @Test
    public void testGetSamplesByCatalogNo() {
    	String catalogNo = "T2573";
        List<SampleDto> samples = OsirisService.getSamplesByCatalogNo(catalogNo);
        Assert.assertTrue(!samples.isEmpty());
        Assert.assertTrue(samples.get(0).getCatalogNo().equals("T2573"));
    }
    
    @Test
    public void testGetSamplesByUserId() {
    	String userId = "BALDODA1";
        List<SampleDto> samples = OsirisService.getSamplesByUserId(userId);
        Assert.assertTrue(!samples.isEmpty());
        Assert.assertTrue(samples.get(0).getUserId().equals("BALDODA1"));
    }     
    
    @Test
    public void testAddSample() {
    	SampleDto sa = new SampleDto();
    	sa.setActNo("ACT-1015-7270");
        sa.setCatalogNo("CATALOG-XXX");
        sa.setCmnt(null);
        sa.setExtReferfence("EXTREF-XXX");
        sa.setLocation("LOC-XXX");
        sa.setLoNo("LOT-XXX");
        sa.setPrimaryPurpose(null);
        sa.setSupplier("SUPPLIER-XXX");
        sa.setUpdDate(new Date());
        sa.setUserId("BENMEKA1");
    	OsirisService.addSample(sa);
    	List<SampleDto> savedSample = OsirisService.getSamplesByUserId("BENMEKA1");
    	Assert.assertTrue(!savedSample.isEmpty());
    	Assert.assertNotNull(savedSample.get(0));
    }
    
    /** EMPLOYEES **/
    
    @Test
    public void testGetAllEmployees() {
        List<EmployeeDto> employees = OsirisService.getAllEmployees();        
        Assert.assertTrue(!employees.isEmpty());
    }
    
    @Test
    public void testGetEmployeeById() {
    	Integer id = 72421;
    	EmployeeDto employeeDto = OsirisService.getEmployeeById(id);
        Assert.assertNotNull(employeeDto);
        Assert.assertTrue(employeeDto.getEmployeeId() == 72421);
    }
    
    @Test
    public void testGetEmployeeByUserId() {
    	String userId = "benmeka1";
    	EmployeeDto employeeDto = OsirisService.getEmployeeByUserId(userId);
        Assert.assertNotNull(employeeDto);
        Assert.assertTrue(employeeDto.getUserName().equals("benmeka1"));
    }
    
    @Test
    public void testGetEmployeesByManagerId() {
    	Integer managerId = 72384;
        List<EmployeeDto> employees = OsirisService.getEmployeesByManagerId(managerId);
        Assert.assertTrue(!employees.isEmpty());
        Assert.assertTrue(employees.iterator().next().getManagerId() == 72384);
    }
    
    @Test
    public void testGetGroupsByUserId() {
    	String userId = "benmeka1";
    	//int userId =  105367;
    	List<String> groups = OsirisService.getGroupsByUserId(userId);
        Assert.assertNotNull(groups);
        Assert.assertTrue(groups.size() > 0);
    }
    
    @Test
    public void testGetTerminologyByCode() {
    	String code = "ROUTE";
    	List<String> terminology = OsirisService.getTerminologyByCode(code);
        Assert.assertNotNull(terminology);
        Assert.assertTrue(!terminology.isEmpty());
    }
    
    @Test
    public void testGetCodeByCodeName() {
    	String codeName = "Route of Administration Response";
    	String code = OsirisService.getSendTerminologyCodeByCodeName(codeName);
        Assert.assertNotNull(code);
        Assert.assertTrue(code.equals("ROUTE"));
    }
    
}
