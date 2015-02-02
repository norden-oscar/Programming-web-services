<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:uni="university"
	xmlns:app="application" xmlns:emp="employment"
	xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:comp="company"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common"
	xmlns:my="mymy" exclude-result-prefixes="uni app my exsl emp comp">

	<xsl:output indent="yes" method="xml" version="1.0" />
	<xsl:strip-space elements="*" />
	<xsl:strip-space elements="$cmp:Office" />
	<my:map>
		<map>
			<item from="A" to="4" />
			<item from="B" to="3" />
			<item from="C" to="2" />
			<item from="D" to="1" />
			<item from="E" to="0" />
			<item from="F" to="0" />
		</map>
	</my:map>


	<xsl:variable name="company"
		select="document('company.xml')" />
	<xsl:variable name="degree"
		select="document('DegreeMall.xml')" />
	<xsl:variable name="employmentOffice"
		select="document('employmentOffice.xml')" />
	<xsl:variable name="application"
		select="document('application.xml')" />
	<xsl:variable name="vMap" select="document('')/*/my:map/*/*" />

	
	<xsl:template match="/">
		<Profile xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="result ../xml/result.xsd" xmlns="result">


			<xsl:apply-templates select="$application/child::node()" />
			<xsl:apply-templates select="$degree/child::node()" />
			<xsl:apply-templates select="$employmentOffice/child::node()" />
			<xsl:apply-templates select="$company/child::node()" />
		</Profile>
	</xsl:template>



	<xsl:template match="uni:program">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="child::node()|@*" />
			<xsl:variable name="gradeVar">
				<xsl:for-each select="uni:course/@grade">
					<grade>
						<xsl:value-of select="$vMap[@from = current()]/@to" />
					</grade>
				</xsl:for-each>
			</xsl:variable>
			<!-- Need to convert rtf to node-set. Using exsl capabileties for that. 
				Didn't find better solution. -->
			<!-- rounded to 3 digits -->
			<gpa>
				<xsl:value-of
					select="round(sum(exsl:node-set($gradeVar)/grade) div
				 sum(uni:course/@creditHours) * 1000) div 1000" />
			</gpa>
		</xsl:element>
	</xsl:template>

	<xsl:template match="emp:Person">
		<xsl:choose>
			<!-- Match found. Matching Application person-id with emplymentOffice 
				records -->
			<xsl:when test="@id=$application/app:Application/app:Person/@id">

			<xsl:element name="{local-name()}">
					<xsl:apply-templates select="child::node()|@*" />
			</xsl:element>
			</xsl:when>
			<!-- Do nothing -->
			<xsl:otherwise>

			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>
	
	<xsl:template match="comp:Company">
		<xsl:choose>

			<xsl:when
				test="@name=$employmentOffice/emp:Records/emp:Person/emp:Work/@company">
				<xsl:element name="{local-name()}">
					<xsl:apply-templates select="child::node()|@*" />
				</xsl:element>
			</xsl:when>

		</xsl:choose>

	</xsl:template>

	<!-- Don't show reports from companies. Hardly relevant -->
	<xsl:template match="comp:Reports">
	</xsl:template>



	<!-- template to copy elements (Don't go for attributes whose name is schemaloc) -->
	<xsl:template match="*">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@*[not(name()='xsi:schemaLocation')] | node()" />
		</xsl:element>
	</xsl:template>

	<!-- template to copy attributes -->
	<xsl:template match="@*">
		<xsl:attribute name="{local-name()}">
            <xsl:value-of select="." />
        </xsl:attribute>
	</xsl:template>


	<!-- template to copy the rest of the nodes -->
	<xsl:template match="comment() | text() | processing-instruction()">
		<xsl:copy />
	</xsl:template>



</xsl:stylesheet>